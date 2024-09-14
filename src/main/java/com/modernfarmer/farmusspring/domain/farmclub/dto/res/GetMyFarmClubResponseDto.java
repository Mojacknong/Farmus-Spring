package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record GetMyFarmClubResponseDto(
        Long farmClubId,
        String farmClubName,
        String farmClubImage,
        Long wholeMemberCount,
        Integer currentStep,
        List<Step> steps,
        String advice,
        Long daysSinceStart
) {

    public static GetMyFarmClubResponseDto of(GetMyFarmClubVo farmClubInfo, List<Step> steps, String advice) {
        return GetMyFarmClubResponseDto.builder()
                .farmClubId(farmClubInfo.farmClubId())
                .farmClubName(farmClubInfo.farmClubName())
                .farmClubImage(farmClubInfo.farmClubImage())
                .wholeMemberCount(farmClubInfo.wholeMemberCount())
                .currentStep(farmClubInfo.currentStep())
                .steps(steps)
                .advice(advice)
                .daysSinceStart(farmClubInfo.daySinceStart())
                .build();
    }

    public static List<Step> createSteps(List<StepVo> stepVoList, List<GetMissionPostListVo> missionList) {
        List<Step> steps = new ArrayList<>();
        for (StepVo step: stepVoList) {
            long count = 0L;
            List<String> images = new ArrayList<>();
            for (GetMissionPostListVo mission: missionList) {
                if (step.num() == mission.stepNum()) {
                    if (!mission.isReported())
                        images.add(mission.image());
                    count++;
                }
            }

            steps.add(new Step(
                    images,
                    step.num(),
                    step.content(),
                    count
            ));
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
