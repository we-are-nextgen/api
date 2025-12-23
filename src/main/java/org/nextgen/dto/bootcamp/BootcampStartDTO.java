package org.nextgen.dto.bootcamp;

import java.time.Instant;
import java.util.UUID;

import org.nextgen.model.Bootcamp;
import org.nextgen.model.BootcampStart;
import org.nextgen.model.BootcampStart.STATUS;

public class BootcampStartDTO {
    public UUID id;
    public Instant startedAt;
    public STATUS status;
    public Bootcamp bootcamp;
    public UUID userId;
    public String email;
    public String cohortName;


    public static BootcampStartDTO tDto(BootcampStart bootcampStart){
        BootcampStartDTO bootcampStartDTO = new BootcampStartDTO();
        bootcampStartDTO.id = bootcampStart.id;
        bootcampStartDTO.startedAt = bootcampStart.startedAt;
        bootcampStartDTO.status = bootcampStart.status;
        bootcampStartDTO.userId = bootcampStart.user.id;
        bootcampStartDTO.email = bootcampStart.user.userId;
        bootcampStartDTO.cohortName = bootcampStart.cohortName;
        //bootcampStartDTO.bootcamp = BootcampDTO.tDto(bootcampStart.bootcamp);
        bootcampStartDTO.bootcamp = bootcampStart.bootcamp;
        return bootcampStartDTO;
    }
    
}
