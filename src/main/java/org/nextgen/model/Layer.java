package org.nextgen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import java.util.List;


@Entity
public class Layer extends BaseEntity {
    
    public String type;        // orientation, core, practice, capstone
    public String name;       // e.g. Orientation, Core Modules

    @ManyToOne
    @JsonbTransient
    public Bootcamp bootcamp;

    @OneToMany(mappedBy = "layer", cascade = CascadeType.ALL)
    public List<Module> modules;
}
