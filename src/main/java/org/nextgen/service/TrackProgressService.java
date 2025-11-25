package org.nextgen.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.nextgen.dto.TrackProgressDTO;
import org.nextgen.model.ITProfessional;
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

        return new TrackProgressDTO(enrolled, completedLabs, progress);
    }

    @Transactional
    public TrackProgressDTO getProgressByEmail(String email, Long trackId) {
        // Find user by email (we use email as unique identifier userId)

        ITProfessional user = ITProfessional.find("userId", email).firstResult();
        if (user == null) {
            return new TrackProgressDTO(false, List.of(), 0);
        }

        return getProgress(user.id, trackId);
    }

}
