package org.nextgen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.transaction.Transactional;

import org.nextgen.dto.TrackProgressDTO;
import org.nextgen.model.Activity;
import org.nextgen.model.Badge;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.Lab;
import org.nextgen.model.LearningTrack;
import org.nextgen.model.Milestone;
import org.nextgen.model.UserBadge;
import org.nextgen.model.UserLabProgress;
import org.nextgen.model.UserProgress;
import org.nextgen.model.UserTrack;
import org.nextgen.model.Observers.UserTrackCreatedEvent;


@ApplicationScoped
public class TrackProgressService {
    

    public TrackProgressDTO getProgress(Long userId, Long trackId) {

        // 1️ Check enrollment
        UserTrack userTrack = UserTrack.find("user.id = ?1 AND learningTrackId = ?2", userId, trackId)
                .firstResult();

        Boolean enrolled = userTrack != null && userTrack.enrolled !=null && userTrack.enrolled;
        Boolean completed = userTrack != null && userTrack.completed !=null && userTrack.completed;

        // 2️ Get all labs of track
        LearningTrack track = LearningTrack.findById(trackId);
        int totalLabs = track.labs != null ? track.labs.size() : 0;

        // 3️ Get completed labs by this user for this track
        List<Object[]> results = Lab.findLabsWithTotalEarnedPointsByTrack(userId,trackId);
        List<Map<String,Long>> completedLabs = new ArrayList<Map<String,Long>>();
        for (Object[] row : results) {
            Map<String,Long> map = new HashMap<String,Long>();
            Lab lab = (Lab) row[0];
            Long totalEarnedPoints = (Long) row[1];
            map.put("labId", lab.id);
            map.put("points", totalEarnedPoints);
            completedLabs.add(map);
        }

        // 4️ Calculate progress
        int progress = 0;
        if (totalLabs > 0) {
            progress = (completedLabs.size() * 100) / totalLabs;
        }

        UserLabProgress labInProgress = UserLabProgress.find(
        "user.id = ?1 AND track.id = ?2 AND completed = false",
        userId, trackId
        ).firstResult();

        return new TrackProgressDTO(enrolled, completedLabs, progress, 
            labInProgress != null ? labInProgress.lab.id : Long.valueOf(0),completed );
    }

    
    public TrackProgressDTO getProgressByEmail(String email, Long trackId) {
        // Find user by email (we use email as unique identifier userId)

        ITProfessional user = ITProfessional.find("userId", email).firstResult();
        if (user == null) {
            return new TrackProgressDTO(false, List.of(), 0, Long.valueOf(0),false );
        }

        return getProgress(user.id, trackId);
    }

   
    public Lab getNextLab(String email, Long trackId) {
        Long userId = ITProfessional.getUserIdByEmail(email);
        LearningTrack track = LearningTrack.findById(trackId);

        var labs = new ArrayList<>(track.labs);
        labs.sort(Comparator.comparing(l -> l.sequence));

        for (Lab lab : labs) {
            boolean done = UserLabProgress.find(
                    "user.id = ?1 AND lab.id = ?2 AND completed = true",
                    userId, lab.id
            ).firstResult() != null;

            if (!done) return lab;
        }

        return null;
    }


    @Transactional
    public UserLabProgress markLabCompleted(String email, Long labId, Long trackId) {        
        ITProfessional user = ITProfessional.getUserByEmail(email);
        Lab lab = Lab.findById(labId);

        if (user == null || lab == null)
            throw new IllegalStateException("User or Lab not found.");

        UserLabProgress progress = UserLabProgress.find("user.id = ?1 AND lab.id = ?2", user.id, labId)
                .firstResult();

        if (progress == null) {
            progress = new UserLabProgress();
            progress.user = user;
            progress.lab = lab;
        }

        progress.completed = true;
        progress.completedAt = LocalDateTime.now();
        progress.persist();
        
        // automatically assign tgh next lab
        Lab nextLab = getNextLab(email,trackId);
        if(nextLab!=null){
            UserLabProgress nextLabprogress = new UserLabProgress();
            nextLabprogress.user = user;
            nextLabprogress.lab = nextLab;
            nextLabprogress.completed = false;
            nextLabprogress.track = progress.track;
            nextLabprogress.startdate = LocalDateTime.now();
            nextLabprogress.persist();
        } else 
        {
            // TODO: handle track compelition and update credits and rewards
            UserTrack userTrack = UserTrack.find("user.id = ?1 AND track.id = ?2", user.id, trackId)
                .firstResult();
            userTrack.finishedAt = LocalDateTime.now();
            userTrack.completed = true;
            userTrack.persist();
            UserProgress userProgress = UserProgress.getLastUserProgressByStatus(user.id, UserProgress.IN_PROGRESS);
            Integer requiredPoints = userProgress.milestone.requiredPoints;
            Long totalEarned = LearningTrack.findTotalEarnedPointsByTrack(user.id, trackId);
            LearningTrack track = LearningTrack.findById(trackId);
            totalEarned+=track.rewardPoints;
            if(totalEarned>=requiredPoints){
                userProgress.status = UserProgress.COMPLETED;
                userProgress.earnedPoints = totalEarned;
                userProgress.closeDate = LocalDateTime.now();
                userProgress.persist();
                // progress to next milestone
                UserProgress newUserProgress = new UserProgress();
                newUserProgress.user = user;
                newUserProgress.status = UserProgress.IN_PROGRESS;
                newUserProgress.startDate = LocalDateTime.now();
                newUserProgress.milestone=userProgress.nextMilestone;
                newUserProgress.nextMilestone =
                    Milestone.findNextMilestone(userProgress.nextMilestone.sequence);
                newUserProgress.prevProgress = userProgress;
                newUserProgress.persist();
            }
            // handle user Journey progress
            // Journey --> Stage --> Milestone          user_progress(milestone)
            // Domain --> Track --> lab --> exercise    user_track/user_lab_progress
            

        }

        return progress;
    }
    
    
    /**
     * entroll user to learnin track
     * @param email
     * @param trackId
     * @return
     */
    @Transactional
    public UserLabProgress enroll(String email, Long trackId) {
        ITProfessional user  = ITProfessional.getUserByEmail(email);
        LearningTrack track = LearningTrack.findById(trackId);
        // Check if already enrolled => find any progress row
        long existingCount = UserLabProgress.count(
            "user.id = ?1 AND track.id = ?2",
            user.id, trackId
        );

        if (existingCount > 0) {
            return null; // already enrolled
        }
        
        // eroll user
        UserTrack userTrack = new UserTrack();
        userTrack.user = user;
        userTrack.learningTrackId = trackId;
        userTrack.enrolled = true;
        userTrack.enrolledAt = LocalDateTime.now();
        userTrack.persist();
        // Only create progress for the first lab, if exists
        if (!track.labs.isEmpty()) {
            Lab firstLab = track.labs.get(0); // first lab
            UserLabProgress p = new UserLabProgress();
            p.user = user;
            p.lab = firstLab;
            p.labId = firstLab.id;
            p.track = track;
            p.completed = false;
            p.startdate = LocalDateTime.now();
            p.persist();
            // update user progress in the journey
            updateJourneyProgress(user, track);
            return p;
        }
        return null;
    }


    
    

    private void updateJourneyProgress(ITProfessional user, LearningTrack track){
        // 1 - get user current progress
        // if first time enrollment, advance with milestone progress
        //Long count = UserProgress.countByUserIdAndActivityName(user.id, Activity.LEARNIN_TRACK_ACTIVITY_NAME); 
        //List<UserProgress> userProgressList = UserProgress.findByUserIdAndActivityName(user.id, ACTIVITY_NAME);

        UserProgress lastUserProgress = UserProgress.findLastByUserIdAndActivityName
                            (user.id, Activity.LEARNING_TRACK_ACTIVITY_NAME); 
        if(lastUserProgress==null)
            lastUserProgress = UserProgress.findLastUserProgress (user.id);
        Integer sequence = lastUserProgress==null?0:lastUserProgress.milestone.sequence;
        Milestone milestone = Milestone.findNextLearningTrackMilestone(sequence);
        if(milestone!=null) {
            UserProgress userProgress = new UserProgress();
            userProgress.user = user;
            userProgress.milestone = milestone;
            Milestone nextMilestone = Milestone.findNextMilestone(milestone.sequence);
            userProgress.nextMilestone = nextMilestone;
            // check if lastUserProgress is null find the prec non learning track one
            userProgress.prevProgress = lastUserProgress;
            userProgress.startDate = LocalDateTime.now();
            userProgress.status = UserProgress.IN_PROGRESS;
            userProgress.persist();
            // update user profile if progresses to a new stage 
            if(user.stage!=milestone.stage)
            {
                user.stage = milestone.stage;
                user.persist();
            }
        }
        // else 
        // check how many points required to achieve this milestone 
    }

}
