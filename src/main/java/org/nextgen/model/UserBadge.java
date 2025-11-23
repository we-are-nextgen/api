package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_badges")
public class UserBadge extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    public Badge badge;

    // Extra column!
    public LocalDateTime awardedAt;
}
