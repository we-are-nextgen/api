package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_badges")
public class UserBadge extends BaseEntity{
    public enum BadgeSource {
        AUTO,
        MANUAL
    }
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    public Badge badge;

    // Extra column!
    public LocalDateTime awardedAt;

    @Enumerated(EnumType.STRING)
    public BadgeSource source;
}
