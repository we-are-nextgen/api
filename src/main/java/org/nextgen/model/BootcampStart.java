package org.nextgen.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "bootcamp_start")
public class BootcampStart extends BaseEntity {

    // The bootcamp reference
    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "bootcamp_id")
    public Bootcamp bootcamp;

    // The user starting the bootcamp
    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "user_id")
    public ITProfessional user;

    // Timestamp of when the bootcamp is started
    @Column(nullable = false, name="started_at")
    public Instant startedAt = Instant.now();

    // Optional: allow "enrollment metadata"
    @Column(name ="cohort_name")
    public String cohortName;

    public enum STATUS {
        SARTED,
        KICKOFF,
        ORIENTATION,
        CORE_MODULES,
        PRACTICE_MODULES,
        COMPLETED
    }

    @Enumerated(EnumType.STRING)
    public STATUS status;

    public static BootcampStart start(String email, Long bootcampId, String cohortName) {
        BootcampStart s = new BootcampStart();
        s.user = ITProfessional.getUserByEmail(email);
        s.bootcamp = Bootcamp.findById(bootcampId);
        s.cohortName = cohortName;
        s.status = STATUS.SARTED;
        s.persist();
        return s;
    }
}
