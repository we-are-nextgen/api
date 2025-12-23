package org.nextgen.dto;

import java.time.Instant;
import java.util.UUID;

import org.nextgen.model.BootcampStart;

public record UserBootcampDTO(
    UUID bootcampId,
    String bootcampName,
    String bootcampVersion,
    BootcampStart.STATUS status,
    Instant startedAt,
    String cohortName,
    Integer durationWeeks,
    UUID bootcampStartId,
    UUID userBootcampId
) {}

