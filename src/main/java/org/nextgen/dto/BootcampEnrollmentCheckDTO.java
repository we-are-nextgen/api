package org.nextgen.dto;

import org.nextgen.model.BootcampStart;

public class BootcampEnrollmentCheckDTO { 
    public Long id;
    public Long bootcampStartId;
    public BootcampStart.STATUS status;
    public Integer capacity;
    public Long numberOfApplicants;
    public boolean amIEnrolled;

    BootcampEnrollmentCheckDTO (
                                Long id,
                                Long bootcampStartId,
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
