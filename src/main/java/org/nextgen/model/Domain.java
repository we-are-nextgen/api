package org.nextgen.model;

import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


/**
 * Enable Track JPA entity defined as a Panache Entity.
 */
@Entity
public class Domain extends BaseEntity {
    public String name;
    public String description;
    public String community;
    public String icon;
    public String tags;

    
    @OneToMany(mappedBy = "domain")
    public List<LearningTrack> tracks;
    
    //@JsonbTransient
    @ManyToMany
    @JoinTable(
    name = "domain_profiles", // name of the join table
    joinColumns = @JoinColumn(name = "domain_id"), // foreign key to this entity
    inverseJoinColumns = @JoinColumn(name = "profile_id") // foreign key to the other entity
    )
    public List <Profile> profiles;
}
