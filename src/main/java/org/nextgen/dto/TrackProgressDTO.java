package org.nextgen.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrackProgressDTO {
    public boolean enrolled;
    public List<Map<String,Object>> completedLabs;
    public int progress;
    public UUID currentLabId;
    public boolean completed;

    public TrackProgressDTO(boolean enrolled, List<Map<String,Object>> completedLabs, int progress, UUID currentLabId
        ,boolean completed
    ) {
        this.enrolled = enrolled;
        this.completedLabs = completedLabs;
        this.progress = progress;
        this.currentLabId = currentLabId;
        this.completed=completed;
    }

    
}
