package org.nextgen.model.Observers;



import org.nextgen.model.BaseProgress;

public class BadgeRewardEvent {
    public enum EventType  {
        INSERT,
        UPDATE
    }
    private final BaseProgress progress;
    private final EventType type;

    public BadgeRewardEvent(BaseProgress progress, EventType type) {
        this.progress = progress;
        this.type = type;
    }

    public BaseProgress getProgressRecrod() {
        return progress;
    }
    public EventType getEventType(){
        return type;
    }

}