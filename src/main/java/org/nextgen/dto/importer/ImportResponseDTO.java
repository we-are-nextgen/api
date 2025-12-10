package org.nextgen.dto.importer;

import java.util.List;

public class ImportResponseDTO {

    private boolean success;
    private String message;
    private List<Long> importedObjects;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<Long> getImportedObjects() { return importedObjects; }
    public void setImportedObjects(List<Long> importedObjects) { this.importedObjects = importedObjects; }
}
