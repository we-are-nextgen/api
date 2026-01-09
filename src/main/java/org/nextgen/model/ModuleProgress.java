package org.nextgen.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "module_progress")
public class ModuleProgress extends BaseProgress {
    
    @ManyToOne
    @JoinColumn(name="layer_progress_id", nullable = false)
    public LayerProgress layerProgress;

    @ManyToOne
    @JoinColumn(name="module_id", nullable = false)
    public Module module;

    @ManyToOne
    @JoinColumn(name="bootcamp_progress_id", nullable = false)
    @JsonbTransient
    public BootcampProgress bootcampProgress;

    public String comments;
    
    public boolean completed;  
}
