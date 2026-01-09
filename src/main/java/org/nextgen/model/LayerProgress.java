package org.nextgen.model;


import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "layer_progress")
public class LayerProgress extends BaseProgress {

    @ManyToOne
    @JoinColumn(name="layer_id", nullable = false)
    public Layer layer;

    @ManyToOne
    @JoinColumn(name="bootcamp_progress_id", nullable = false)
    @JsonbTransient
    public BootcampProgress bootcampProgress;

    public String comments;
    
    public boolean completed;  
    
}
