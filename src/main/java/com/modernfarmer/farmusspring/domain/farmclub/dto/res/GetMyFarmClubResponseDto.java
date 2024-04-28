package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImagesVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record GetMyFarmClubResponseDto(
        String farmClubName,
        String farmClubImage,
        Long wholeMemberCount,
        List<Step> steps,
        String advice,
        int daysSinceStart
) {

    public static GetMyFarmClubResponseDto of(GetMyFarmClubVo farmClubInfo, List<Step> steps, String advice) {
        return GetMyFarmClubResponseDto.builder()
                .farmClubName(farmClubInfo.farmClubName())
                .farmClubImage(farmClubInfo.farmClubImage())
                .wholeMemberCount(farmClubInfo.wholeMemberCount())
                .steps(steps)
                .advice(advice)
                .daysSinceStart(farmClubInfo.daySinceStart())
                .build();
    }

    public static List<Step> createSteps(List<StepVo> stepVoList, List<GetMissionPostListWithStepCountsAndImagesVo> missionList) {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < stepVoList.size(); i++) {
            Step step = new Step(
                    missionList.get(i).images(),
                    stepVoList.get(i).num(),
                    stepVoList.get(i).content(),
                    missionList.get(i).count()
            );
            steps.add(step);
        }
        return steps;
    }

    public record Step(
            List<String> images,
            int stepNum,
            String stepName,
            Long completeMemberCount
    ) {

    }
}
