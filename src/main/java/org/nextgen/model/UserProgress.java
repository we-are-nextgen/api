package org.nextgen.model;

import java.sql.Date;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * Enable UserProgress JPA entity defined as a Panache Entity.
 */
@Entity
@Table (name = "user_progress" )
public class UserProgress extends BaseEntity {

    @ManyToOne    
    public Milestone milestone;
    public Date startDate;
    public Date closeDate;
    public String status; // e.g., "in-progress", "completed"
    
    @ManyToOne
    public Milestone nextMilestone; // next milestone to achieve
    @ManyToOne
    public UserProgress prevProgress; // previous progress record

    @ManyToOne
    @JsonbTransient
    public ITProfessional user;

}
