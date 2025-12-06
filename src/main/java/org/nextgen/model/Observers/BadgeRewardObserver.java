package org.nextgen.model.Observers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;

import org.nextgen.model.UserTrack;

@ApplicationScoped
public class BadgeRewardObserver {
    @Inject
    Event<UserTrackCreatedEvent> eventBus;

    @PostPersist
    public void userCreated(Object entity) {
        if (entity instanceof UserTrack) {
            // STOP! Do not do DB queries here.
            // Just fire the event.
            eventBus.fire(new UserTrackCreatedEvent((UserTrack) entity));
        }
    }

}