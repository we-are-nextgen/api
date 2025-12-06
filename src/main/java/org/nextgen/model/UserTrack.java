package org.nextgen.model;

import java.time.LocalDateTime;

import org.nextgen.model.Observers.BadgeRewardObserver;
import org.nextgen.model.Observers.UserTrackCreatedEvent;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

/**
 * 
 */
@Entity
@Table(name = "user_tracks")
@EntityListeners(BadgeRewardObserver.class)
public class UserTrack extends BaseProgress {
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JoinColumn(name = "learning_track_id", insertable = false, updatable = false)
    @JsonbTransient
    public LearningTrack track;

    @Column(name = "learning_track_id")
    public Long learningTrackId;

    public Long getLearningTrackId() {
        return learningTrackId;
    }

    public void setLearningTrackId(Long learningTrackId) {
        this.learningTrackId = learningTrackId;
    }

    // Extra column!
    public Boolean enrolled;

    public Boolean completed;

    @Column(name = "enrolled_at")
    public LocalDateTime enrolledAt;

    @Column(name = "finished_at")
    public LocalDateTime finishedAt;

    
    // Run this ONLY after the UserTrack is successfully committed to DB
    // This gives you a fresh transaction and prevents the AssertionFailure
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void onUserTrackCreated(@Observes(during = TransactionPhase.AFTER_SUCCESS) UserTrackCreatedEvent event) {

        UserTrack userTrack = event.getUserTrack();
        
        // find the entrolled to track
        LearningTrack enrolledTrack = LearningTrack.findById(userTrack.learningTrackId);

        // if badgeRuleValue=="*" it will fetch the generic ENROLL_TO_TRACK badge
        // otherwise if there is specific value ( ALPHA_TRACK for example) then
        // a badge with same badgeRuleValue must be there, if not
        // then no badges will be rewarded
        if (enrolledTrack != null && enrolledTrack.badgeRuleValue != null) {
            Badge badge = Badge.findByRule(
                Badge.BadgeRuleType.ENROLL_TO_TRACK,
                enrolledTrack.badgeRuleValue
            );
            if (badge != null) {
                UserBadge.award(userTrack.user, badge);
            }
        }
    }



}
