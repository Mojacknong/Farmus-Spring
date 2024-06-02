package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record SearchFarmClubResponseDto (
        Long id,
        String name,
        String veggieName,
        String veggieImage,
        String difficulty,
        String startedAt,
        Integer maxUser,
        Long curUser
) {
    @QueryProjection
    public SearchFarmClubResponseDto(FarmClub farmClub, Long curUser) {
        this(
                farmClub.getId(),
                farmClub.getName(),
                farmClub.getVeggieName(),
                farmClub.getVeggieImage(),
                farmClub.getDifficulty(),
                farmClub.getStartedAt().toString(),
                farmClub.getMaxUser(),
                curUser
        );
    }
}
