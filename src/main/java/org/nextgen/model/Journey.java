package org.nextgen.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.QueryHint;
import jakarta.persistence.NamedQuery;

import java.util.List;

@Entity
@NamedQuery(name = "Journey.findAll", query = "SELECT t FROM Journey t LEFT JOIN FETCH t.stages", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Journey extends BaseEntity {
    public String name;
    public String title;
    public String description;
    public String tags;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List <Stage> stages;
}
