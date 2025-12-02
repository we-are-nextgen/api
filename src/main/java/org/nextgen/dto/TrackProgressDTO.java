package org.nextgen.dto;

import java.util.List;
import java.util.Map;

public class TrackProgressDTO {
    public boolean enrolled;
    public List<Map<String,Long>> completedLabs;
    public int progress;
    public Long currentLabId;
    public boolean completed;

    public TrackProgressDTO(boolean enrolled, List<Map<String,Long>> completedLabs, int progress, Long currentLabId
        ,boolean completed
    ) {
        this.enrolled = enrolled;
        this.completedLabs = completedLabs;
        this.progress = progress;
        this.currentLabId = currentLabId;
        this.completed=completed;
    }

    
}
