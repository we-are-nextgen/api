package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
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
    @JsonbTransient
    public BootcampStart bootcampStart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;


    
    @Column(name = "join_date")
    public LocalDateTime joinDate;

    @Column(name = "end_date")
    public LocalDateTime endDate;
    
    public UserBootcamp(){

    }

    public UserBootcamp(ITProfessional user,BootcampStart bootcampStart){
        this.bootcampStart=bootcampStart;
        this.user=user;
        // join date will be assigned today's date
        this.joinDate = LocalDateTime.now();
    }

    public static UserBootcamp findByUserAndBootcampStart(BootcampStart bootcampStart, ITProfessional user){
        return UserBootcamp.find("user=?1 and bootcampStart=?2", user, bootcampStart).firstResult();
    }

}
