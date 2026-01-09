package org.nextgen.dto.bootcamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.nextgen.model.BootcampProgress;
import org.nextgen.model.UserBootcamp;

public class UserBootcampDTO {
    public UUID id;
    public LocalDateTime joinDate;
    public LocalDate endDate;
    public UUID userId;
    public String email;
    public BootcampStartDTO bootcampStart;
    public BootcampProgressDTO bootcampProgress;


    public static UserBootcampDTO tDto(UserBootcamp userBootcamp, BootcampProgress progress){
        UserBootcampDTO userBootcampDTO = new UserBootcampDTO();
        userBootcampDTO.id = userBootcamp.id;
        userBootcampDTO.joinDate = userBootcamp.joinDate;
        userBootcampDTO.userId = userBootcamp.user.id;
        userBootcampDTO.email = userBootcamp.user.userId;
        userBootcampDTO.bootcampStart =  BootcampStartDTO.tDto(userBootcamp.bootcampStart);
        if (progress != null){
            userBootcampDTO.bootcampProgress = BootcampProgressDTO.tDto(progress);
        }   
        return userBootcampDTO;
    }
}
