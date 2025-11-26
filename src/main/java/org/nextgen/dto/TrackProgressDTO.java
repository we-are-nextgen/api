package org.nextgen.dto;

import java.util.List;

public class TrackProgressDTO {
    public boolean enrolled;
    public List<Long> completedLabs;
    public int progress;
    public Long currentLabId;

    public TrackProgressDTO(boolean enrolled, List<Long> completedLabs, int progress, Long currentLabId) {
        this.enrolled = enrolled;
        this.completedLabs = completedLabs;
        this.progress = progress;
        this.currentLabId = currentLabId;
    }
    
}
