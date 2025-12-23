package org.nextgen.dto.bootcamp;

import java.util.UUID;

import org.nextgen.model.Bootcamp;

public class BootcampDTO {
    public UUID id;
    public String name;
    public String description;
    public Integer durationWeeks;

    public static BootcampDTO tDto(Bootcamp bootcamp){
        BootcampDTO bootcampDTO = new BootcampDTO();
        //bootcampDTO.name = bootcamp.name;
        //bootcampDTO.description = bootcamp.description;
        return bootcampDTO;
    }
}
