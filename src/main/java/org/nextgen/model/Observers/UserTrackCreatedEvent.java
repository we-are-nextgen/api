package org.nextgen.model.Observers;


import org.nextgen.model.UserTrack;

public class UserTrackCreatedEvent {
    
    private final UserTrack userTrack;

    public UserTrackCreatedEvent(UserTrack userTrack) {
        this.userTrack = userTrack;
    }

    public UserTrack getUserTrack() {
        return userTrack;
    }
}