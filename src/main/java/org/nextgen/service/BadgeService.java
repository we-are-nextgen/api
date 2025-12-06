package org.nextgen.service;

import org.nextgen.model.ITProfessional;
import org.nextgen.model.Milestone;
import org.nextgen.model.Stage;
import org.nextgen.model.Badge;
import org.nextgen.model.BadgeCriteria;
import org.nextgen.model.UserBadge;
import org.nextgen.model.BaseProgress;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BadgeService {

    /**
     * Triggered every time a user progress record is updated (Completed/In-Progress/Skipped).
     */
    @Transactional
    public void evaluateBadges(ITProfessional user, BaseProgress progress) {
        // load all badge rules
        List<Badge> badgeRules = Badge.listAll();
        for (Badge rule : badgeRules) {
            if (isBadgeAlreadyAwarded(user, rule)) {
                continue; // Skip already awarded
            }
            if (meetsCriteria(user, rule)) {
                awardBadge(user, rule, progress);
            }
        }
    }

    /**
     * Check if user already earned this badge.
     */
    private boolean isBadgeAlreadyAwarded(ITProfessional user, Badge badge) {
        long count = UserBadge.count("user = ?1 and badge = ?2", user, badge);
        return count > 0;
    }

    /**
     * Core rule checking logic.
     */
    public boolean meetsCriteria(ITProfessional user, Badge badge) {
        return switch (badge.ruleType) {
            case  COMPLETE_MILESTONE -> {
                Long milestoneId = Long.parseLong(badge.ruleValue);
                boolean ok = user.userProgress.stream()
                        .anyMatch(p -> p.milestone.id.equals(milestoneId)
                                     && p.status.equals("Completed"));
                yield ok;
            }

            case COMPLETE_STAGE -> {
                Long stageId = Long.parseLong(badge.ruleValue);

                boolean ok = user.userProgress.stream()
                        .filter(p -> p.milestone.stageId.equals(stageId))
                        .allMatch(p -> p.status.equals("Completed"));

                yield ok;
            }
            
            default -> false;
        };
    }

    /**
     * Awards the badge to the user.
     */
    @Transactional
    public void awardBadge(ITProfessional user, Badge badge, BaseProgress progress) {
        UserBadge.award(user, badge);
        System.out.println(" Awarded badge '" + badge.name + "' to user " + user.userId);
    }

}