package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseRewardable extends BaseEntity{

    @Column (name ="badge_rule_value")
    public String badgeRuleValue;    
}
