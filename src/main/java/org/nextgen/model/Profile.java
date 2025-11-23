package org.nextgen.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;


/**
 * Enable Profile JPA entity defined as a Panache Entity.
 */
@Entity
public class Profile extends BaseEntity {
    public String name;
    public String description;
    public String skills; 
    public String tags;

    // Existing relationship with Track (inverse side)
    // The relationship is owned by the Track entity, so we use mappedBy.
    @ManyToMany(mappedBy = "profiles")
    @JsonbTransient
    public List<Domain> domains;

    // New inverse relationship with Lab
    // The relationship is owned by the Lab entity, so we use mappedBy="profiles"
    @ManyToMany(mappedBy = "profiles")
    @JsonbTransient
    public List<LearningTrack> labs;

    @ManyToMany(mappedBy = "selectedProfiles")
    @JsonbTransient
    public List<ITProfessional> users;

    public static List<Profile> findByName(String name) {
        return list("name", name);
    }
}