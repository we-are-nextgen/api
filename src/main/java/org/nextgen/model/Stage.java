package org.nextgen.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
public class Stage extends RewardableBase {
    public String name;
    public String description;
    public String icon;
    public Integer sequence;
    public String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", insertable = false, updatable = false)
    @JsonbTransient
    public Journey journey;

    @Column(name = "journey_id")
    private Long journeyId;   // <-- reference only ID

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Milestone> milestones;

    public Long getJourneyId() {
    return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

}