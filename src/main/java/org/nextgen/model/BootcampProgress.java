package org.nextgen.model;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;

import java.time.Instant;
import java.util.Map;
import java.util.HashMap;

@Entity
@Table(name = "bootcamp_progress")
public class BootcampProgress extends BaseEntity {

    // Relation to the bootcamp start event
    @ManyToOne(optional = false)
    @JoinColumn(name = "bootcamp_start_id")
    public BootcampStart bootcampStart;

    // Track layer progress
    @ElementCollection
    @CollectionTable(name = "layer_progress", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "layer_name")
    @Column(name = "completed")
    public Map<String, Boolean> layersCompleted = new HashMap<>();

    // Track module progress
    @ElementCollection
    @CollectionTable(name = "module_progress", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "module_id")
    @Column(name = "completed")
    public Map<String, Boolean> modulesCompleted = new HashMap<>();

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
        int total = modulesCompleted.size();
        if (total > 0) {
            long done = modulesCompleted.values().stream().filter(v -> v).count();
            this.progressPercentage = (int) ((done * 100.0) / total);
        }
    }

    public void markModuleComplete(String moduleId) {
        modulesCompleted.put(moduleId, true);
        updateProgress();
    }

    public void markLabComplete(String labId) {
        labsCompleted.put(labId, true);
    }

    public void markQuizScore(String moduleId, int score) {
        quizScores.put(moduleId, score);
    }

    public void markLayerComplete(String layerName) {
        layersCompleted.put(layerName, true);
    }

    public void completeBootcamp() {
        this.progressPercentage = 100;
        this.completedAt = Instant.now();
    }
}
