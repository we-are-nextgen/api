package org.nextgen.model;

import jakarta.persistence.Entity;

@Entity
public class Activity extends BaseEntity{
    public static String LEARNING_TRACK_ACTIVITY_NAME = "Learning Track";

    public String name;
    public String description;
}
