package org.nextgen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.nextgen.dto.TrackProgressDTO;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.Lab;
import org.nextgen.model.LearningTrack;
import org.nextgen.model.UserLabProgress;
import org.nextgen.model.UserTrack;


@ApplicationScoped
public class TrackProgressService {

    @Transactional
    public TrackProgressDTO getProgress(Long userId, Long trackId) {

        // 1️⃣ Check enrollment
        UserTrack userTrack = UserTrack.find("user.id = ?1 AND learningTrackId = ?2", userId, trackId)
                .firstResult();

        Boolean enrolled = userTrack != null && userTrack.enrolled !=null && userTrack.enrolled;
        Boolean completed = userTrack != null && userTrack.completed !=null && userTrack.completed;

        // 2️⃣ Get all labs of track
        LearningTrack track = LearningTrack.findById(trackId);
        int totalLabs = track.labs != null ? track.labs.size() : 0;

        // 3️⃣ Get completed labs by this user for this track
        List<UserLabProgress> progressList = UserLabProgress.find(
        "user.id = ?1 AND track.id = ?2 AND completed = true",
        userId, trackId
        ).list();

        List<Long> completedLabs = progressList.stream()
                .map(p -> p.lab.id)
                .toList();

        // 4️⃣ Calculate progress
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

    @Transactional
    public TrackProgressDTO getProgressByEmail(String email, Long trackId) {
        // Find user by email (we use email as unique identifier userId)

        ITProfessional user = ITProfessional.find("userId", email).firstResult();
        if (user == null) {
            return new TrackProgressDTO(false, List.of(), 0, Long.valueOf(0),false );
        }

        return getProgress(user.id, trackId);
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
        }


        return progress;
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
            return p;
        }
        return null;

        // Create progress per lab
        /*for (Lab lab : track.labs) {
            UserLabProgress p = new UserLabProgress();
            p.user = user;
            p.lab = lab;
            p.track = track;
            p.completed = false;
            p.startdate = LocalDateTime.now();
            p.persist();
        }*/
    }

}
