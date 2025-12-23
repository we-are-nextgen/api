package org.nextgen.dto;

import java.util.List;
import java.util.UUID;

public class LabSubmissionDTO {
    public String userId;
    public UUID labId;
    public List<ExerciseSubmissionDTO> submissions;
}
