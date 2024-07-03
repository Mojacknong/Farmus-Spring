package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetHelpAllResponseDto(
        VeggieInfo.Help help,
        List<StepVo> steps
) {
    public static GetHelpAllResponseDto of(VeggieInfo.Help help, List<StepVo> steps) {
        return builder()
                .help(help)
                .steps(steps)
                .build();
    }
}
