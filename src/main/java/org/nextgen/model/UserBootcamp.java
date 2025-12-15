package org.nextgen.model;

import java.time.LocalDateTime;
import java.util.List;

import org.nextgen.dto.UserBootcampDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "user_bootcamp" )
public class UserBootcamp extends BaseProgress {
    
    @ManyToOne
    @JoinColumn(name = "bootcamp_start_id")
    BootcampStart bootcampStart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    ITProfessional user;


    @Column(name = "join_date")
    public LocalDateTime joinDate;

    @Column(name = "end_date")
    public LocalDateTime endDate;
    
    public UserBootcamp(){

    }

    public UserBootcamp(ITProfessional user,BootcampStart bootcampStart){
        this.bootcampStart=bootcampStart;
        this.user=user;
        this.joinDate = LocalDateTime.now();
    }

}
