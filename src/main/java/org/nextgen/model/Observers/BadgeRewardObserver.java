package org.nextgen.model.Observers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.transaction.Transactional;

import org.nextgen.model.Badge;
import org.nextgen.model.BaseEntity;
import org.nextgen.model.BaseProgress;
import org.nextgen.model.LearningTrack;
import org.nextgen.model.UserBadge;
import org.nextgen.model.UserLabProgress;
import org.nextgen.model.UserTrack;

@ApplicationScoped
public class BadgeRewardObserver {
    @Inject
    Event<BadgeRewardEvent> eventBus;

    // insert Listener
    @PostPersist
    public void recordCreated(Object entity) {
        if (entity instanceof BaseProgress) {
            // STOP! Do not do DB queries here.
            // Just fire the event.
            eventBus.fire(new BadgeRewardEvent((BaseProgress) entity, BadgeRewardEvent.EventType.INSERT));
        }
    }

    // update Listener
    @PostUpdate
    public void recordUpdated(Object entity) {
        if (entity instanceof BaseProgress) {
            // STOP! Do not do DB queries here.
            // Just fire the event.
            eventBus.fire(new BadgeRewardEvent((BaseProgress) entity,BadgeRewardEvent.EventType.UPDATE));
        }
    }

    // Run this ONLY after the UserTrack is successfully committed to DB
    // This gives you a fresh transaction and prevents the AssertionFailure
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void onUserTrackCreated(@Observes(during = TransactionPhase.AFTER_SUCCESS) BadgeRewardEvent event) {

        BaseEntity baseEntity = (BaseEntity)event.getProgressRecrod();

        switch (event.getEventType()) {
            case BadgeRewardEvent.EventType.INSERT:    
                // Enroll to Track
                if(baseEntity instanceof UserTrack) {
                    UserTrack userTrack = (UserTrack)baseEntity;
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
                else if(baseEntity instanceof UserLabProgress){
                    UserLabProgress UserLabProgress = (UserLabProgress) baseEntity;
                    System.out.println(UserLabProgress.lab.name);
                    //if(UserLabProgress.c)
                }


                break;
            case BadgeRewardEvent.EventType.UPDATE:
                if(baseEntity instanceof UserTrack) {
                    UserTrack userTrack = (UserTrack)baseEntity;
                    // if user track is completed and there is a badge associated
                    // then user will be awarded unless it as already awarded
                    if(userTrack.completed){
                        LearningTrack completedTrack = userTrack.track;
                        Badge badge = Badge.findByRule(
                            Badge.BadgeRuleType.COMPLETE_TRACK,
                            completedTrack.badgeRuleValue
                        );
                        if (badge != null) {
                            UserBadge.award(userTrack.user, badge);
                        }
                    } 
                } else if(baseEntity instanceof UserLabProgress){
                        UserLabProgress userLabProgress = (UserLabProgress) baseEntity;
                        if(userLabProgress.completed){
                            Badge badge = Badge.findByRule(
                                Badge.BadgeRuleType.COMPLETE_LAB,
                                userLabProgress.lab.badgeRuleValue
                            );
                            if (badge != null) {
                                UserBadge.award(userLabProgress.user, badge);
                            }
                        }
                        //System.out.println(UserLabProgress.lab.name);
                    }
                break;        
            default:
                break;
        }

       
    }

}