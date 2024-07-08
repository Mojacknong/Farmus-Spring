package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetHelpAllResponseDto(
        String veggieName,
        String backgroundColor,
        VeggieInfo.Help help,
        List<VeggieInfo.Step> steps
) {
    public static GetHelpAllResponseDto of(VeggieInfo veggieInfo) {
        return builder()
                .veggieName(veggieInfo.getName())
                .backgroundColor(veggieInfo.getBackgroundColor())
                .help(veggieInfo.getHelp())
                .steps(veggieInfo.getSteps())
                .build();
    }
}
