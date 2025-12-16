package org.nextgen.dto;

import java.time.Instant;
import java.util.List;

import org.nextgen.model.BootcampStart;

public record UserBootcampDTO(
    Long bootcampId,
    String bootcampName,
    String bootcampVersion,
    BootcampStart.STATUS status,
    Instant startedAt,
    String cohortName,
    Integer durationWeeks,
    Long bootcampStartId
) {}

