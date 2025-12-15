package org.nextgen.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import java.time.LocalDate;
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
    
    @Column(name ="expected_start_date")
    public LocalDate expectedStartDate;

    @ElementCollection
    public List<String> audience;

    @ElementCollection
    public List<String> outcomes;
    
    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL)
    public List<Layer> layers;

    @ManyToOne
    ITProfessional mentor;

    public static List<Bootcamp> findAllPaginated(int page, int pageSize) {
    return findAll()
        .page(page, pageSize)
        .list();
    }


}
