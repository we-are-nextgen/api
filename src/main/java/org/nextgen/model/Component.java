package org.nextgen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Component extends BaseEntity {
    public String type;        // primer, demo, lab, quiz, discussion
    public String reference;   // file path, lab ID, video URL, etc.

    @ManyToOne
    public Module module;
}
