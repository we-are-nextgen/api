package org.nextgen.model;

import jakarta.persistence.Entity;

@Entity 
public class Badge extends BaseEntity {

    public String name;

    public String title;

    public String icon;

    public String trigger;

    public String rarity;
}
