package org.nextgen.dto;

import java.util.List;

public class ImportResponseDTO {

    private boolean success;
    private String message;
    private List<Long> createdTrackIds;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<Long> getCreatedTrackIds() { return createdTrackIds; }
    public void setCreatedTrackIds(List<Long> createdTrackIds) { this.createdTrackIds = createdTrackIds; }
}
