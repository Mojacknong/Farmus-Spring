package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import lombok.Builder;

@Builder
public record GetRecommendVeggieDto(
        String image,
        String name,
        String difficulty,
        String period
) {
    public static GetRecommendVeggieDto of(String image, String name, String difficulty, String period) {
        return GetRecommendVeggieDto.builder()
                .image(image)
                .name(name)
                .difficulty(difficulty)
                .period(period)
                .build();
    }
}
