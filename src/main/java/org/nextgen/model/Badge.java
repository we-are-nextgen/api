package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity 
public class Badge extends BaseEntity {

    public enum BadgeCategory {
        MILESTONE,
        STAGE,
        SKILL,
        COMMUNITY,
        JOURNEY,
        SPECIAL
    }
    public enum BadgeLevel {
        NONE,
        BRONZE,
        SILVER,
        GOLD
    }

    public String name;

    @Column(unique = true)
    public String code;     //"ONBOARDING_COMPLETE", "FIRST_LEARNING_TRACK_COMPLETE", "MILESTONE_COMPLETE" e.g.

    public String title;

    public String icon;

    public String trigger;

    public String rarity;

    @Enumerated(EnumType.STRING)
    public BadgeCategory category;

    @Enumerated(EnumType.STRING)
    public BadgeLevel level;
}
