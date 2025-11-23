package org.nextgen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lab_asset")
public class Asset extends BaseEntity {

    public String path;     // stored path / URL
    public String type;     // e.g., "image", "code", "pdf"

    @ManyToOne
    @JoinColumn(name = "lab_id")
    public Lab lab;
}
