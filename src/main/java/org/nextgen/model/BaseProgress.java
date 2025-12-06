package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseProgress extends BaseEntity {

    @Column (name ="earned_points")
    public Long earnedPoints;
    
}
