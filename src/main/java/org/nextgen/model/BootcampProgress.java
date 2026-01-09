package org.nextgen.model;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "bootcamp_progress")
public class BootcampProgress extends BaseProgress {

    // Relation to the bootcamp start event
    @ManyToOne(optional = false)
    @JoinColumn(name = "bootcamp_start_id")
    public BootcampStart bootcampStart;

    // Track layer progress
    @OneToMany(mappedBy = "bootcampProgress")
    public List<LayerProgress> completedLayers = new ArrayList<>();

    // Track module progress
    @OneToMany(mappedBy = "bootcampProgress")
    public List<ModuleProgress> completedModules = new ArrayList<>();

    /* 
    @ElementCollection
    @CollectionTable(name = "module_progress", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "module_id")
    @Column(name = "completed")
    public Map<String, Boolean> modulesCompleted = new HashMap<>();*/

    // Track quiz scores per module
    @ElementCollection
    @CollectionTable(name = "quiz_scores", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "module_id")
    @Column(name = "score")
    public Map<String, Integer> quizScores = new HashMap<>();

    // Labs completed
    @ElementCollection
    @CollectionTable(name = "lab_progress", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "lab_id")
    @Column(name = "completed")
    public Map<String, Boolean> labsCompleted = new HashMap<>();

    // Overall progress percentage
    @Column(nullable = false, name="progress_percentage")
    public int progressPercentage = 0;

    // Completed timestamp
    @Column(name="completed_at")
    public Instant completedAt;

    public void updateProgress() {
        int total = completedModules.size();
        if (total > 0) {
            long done = completedModules.stream()
                                        .filter(mp -> mp.completed)
                                        .count();
            this.progressPercentage = (int) ((done * 100.0) / total);
        }
    }

    public void initializeLayer(UUID layerId) {
        LayerProgress layerProgress = new LayerProgress();
        layerProgress.layer = Layer.findById(layerId);
        layerProgress.bootcampProgress = this;
        completedLayers.add(layerProgress);
        layerProgress.completed = false;
        layerProgress.persist();
        
        layerProgress.layer.modules.forEach(module -> {
            initializeModule(module.id, layerProgress);
        });
    }

    public void markLayerComplete(UUID layerId) {
        for (LayerProgress lp : completedLayers) {
            if (lp.layer.id.equals(layerId)) {
                lp.completed = true;
                lp.persist();
                break;
            }
        }
    }

    public void initializeModule(UUID moduleId, LayerProgress layerProgress) {
        ModuleProgress moduleProgress = new ModuleProgress();
        moduleProgress.module = Module.findById(moduleId);  
        moduleProgress.bootcampProgress = this;
        moduleProgress.layerProgress = layerProgress;
        completedModules.add(moduleProgress);
        moduleProgress.completed = false;
        moduleProgress.persist();  
    }

    public void markModuleComplete(UUID moduleProgressId) {
        ModuleProgress moduleProgress = ModuleProgress.findById(moduleProgressId);  
        moduleProgress.completed = true;
        moduleProgress.persist();
        updateProgress();
    }

    public void markLabComplete(String labId) {
        labsCompleted.put(labId, true);
    }

    public void markQuizScore(String moduleId, int score) {
        quizScores.put(moduleId, score);
    }


    public void completeBootcamp() {
        this.progressPercentage = 100;
        this.completedAt = Instant.now();
    }
}


