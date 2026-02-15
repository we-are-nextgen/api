package org.nextgen.dto;

import java.util.UUID;

import org.nextgen.model.BootcampStart;

public class BootcampEnrollmentCheckDTO { 
    public UUID id;
    public UUID bootcampStartId;
    public BootcampStart.STATUS status;
    public Integer capacity;
    public Long numberOfApplicants;
    public boolean amIEnrolled;
    public UUID userBootcampId;
    
    public BootcampEnrollmentCheckDTO() {}

    BootcampEnrollmentCheckDTO (
                                UUID id,
                                UUID bootcampStartId,
                                BootcampStart.STATUS status,
                                Integer capacity,
                                Long numberOfApplicants){
        this.id=id;
        this.bootcampStartId=bootcampStartId;
        this.status=status;
        this.capacity = capacity;
        this.numberOfApplicants=numberOfApplicants;
    }
}
