package org.nextgen.model;

import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Team extends BaseEntity{
    public String name;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "admin_user_id", insertable = false, updatable = false)
    public ITProfessional admin;

    @ManyToMany
    @JoinTable(
    name = "team_members", // name of the join table
    joinColumns = @JoinColumn(name = "team_id"), // foreign key to this entity
    inverseJoinColumns = @JoinColumn(name = "user_id") // foreign key to the other entity
    )
    public List<ITProfessional> members;
    
}
