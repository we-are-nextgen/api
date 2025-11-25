package org.nextgen.dto;

import java.util.List;

public class TrackProgressDTO {
    public boolean enrolled;
    public List<Long> completedLabs;
    public int progress;

    public TrackProgressDTO(boolean enrolled, List<Long> completedLabs, int progress) {
        this.enrolled = enrolled;
        this.completedLabs = completedLabs;
        this.progress = progress;
    }
    
}
