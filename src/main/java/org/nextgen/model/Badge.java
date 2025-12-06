package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity 
public class Badge extends BaseEntity {
    /**
     * Badge Category
     */
    public enum BadgeCategory {
        MILESTONE,
        STAGE,
        SKILL,
        COMMUNITY,
        JOURNEY,
        SPECIAL
    }

    /**
     * Badge Level [optional and not yet decided]
     */
    public enum BadgeLevel {
        NONE,
        BRONZE,
        SILVER,
        GOLD
    }

    /**
     * Badge rules to check achievment type
     */
    public enum BadgeRuleType {
        COMPLETE_PROFILE,
        ENROLL_TO_TRACK,
        COMPLETE_LAB,
        COMPLETE_TRACK,
        COMPLETE_MILESTONE,
        COMPLETE_ALL_MILESTONES_IN_STAGE,
        COMPLETE_STAGE,
        COMPLETE_JOURNEY,
        POINTS_REACHED
    }

    /**
     * badge name
     */
    public String name;

    public String title;

    public String icon;

    public String rarity;

    @Enumerated(EnumType.STRING)
    public BadgeCategory category;

    @Enumerated(EnumType.STRING)
    public BadgeLevel level;

    @Column (name ="rule_type")
    @Enumerated(EnumType.STRING)
    public BadgeRuleType ruleType;

    /**
     * value of this filed will be compared to the badgeRuleValue field in all
     * qualified objects, those objects must extend RewardableBase abstract base class
     * for example:
     * if rule_type = POINTS_REACHED and rule_value=points=300 then the badge will be earned
     */
    @Column (name ="rule_value")
    public String ruleValue;  // e.g. milestoneId, stageId, points=300, [* means apply for any value]

    /* ---- Model Helper Methods */

    /**
     * find by ruleType
     * @param ruleType
     * @return
     */
    public static Badge findByRuleType(BadgeRuleType ruleType) {
        return find("ruleType", ruleType).firstResult();
    }

    /**
     * find by ruletype and rulevalue
     * @param type
     * @param value
     * @return
     */
    public static Badge findByRule(BadgeRuleType type, String value) {
        Badge badge = find("ruleType = ?1 AND ruleValue = ?2", type, value).firstResult();
        return badge;
    }

}
