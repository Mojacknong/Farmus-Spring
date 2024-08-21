package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetHelpAllResponseDto(
        String veggieName,
        String veggieImage,
        String backgroundColor,
        VeggieInfo.Help help,
        List<VeggieInfo.Step> steps
) {
    public static GetHelpAllResponseDto of(VeggieInfo veggieInfo) {
        return builder()
                .veggieName(veggieInfo.getName())
                .veggieImage(veggieInfo.getVeggieImage())
                .backgroundColor(veggieInfo.getBackgroundColor())
                .help(veggieInfo.getHelp())
                .steps(veggieInfo.getSteps())
                .build();
    }
}
