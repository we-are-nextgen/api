package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
public class Milestone extends BaseEntity {
    public String name;
    public String description;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "stage_id", insertable = false, updatable = false)
    public Stage stage;

    @Column(name = "stage_id")
    private Long stageId;   // <-- reference only ID

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

}