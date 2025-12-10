package org.nextgen.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import java.util.List;

/**
 * Bootcamp
 * └── Layer (orientation, core, practice, capstone)
 *       └── Module (C1, C2, P1…)
 *             └── Component (primer, lab, demo, quiz…)
 */
@Entity
@Table (name = "bootcamp" )
public class Bootcamp extends BaseRewardable {
    public String name;
    public String version;
    public String description;
    public Integer durationWeeks;

    @ElementCollection
    public List<String> audience;

    @ElementCollection
    public List<String> outcomes;

    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL)
    public List<Layer> layers;
}
