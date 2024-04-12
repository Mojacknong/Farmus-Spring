package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImages;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;

import java.util.ArrayList;
import java.util.List;

public record GetMyFarmClubResponseDto(
        String farmClubName,
        String farmClubImage,
        Long wholeMemberCount,
        List<Step> steps,
        String advice,
        int daysSinceStart
) {

    public static GetMyFarmClubResponseDto of(GetMyFarmClubVo farmClubInfo, List<Step> steps, String advice) {
        return new GetMyFarmClubResponseDto(
                farmClubInfo.farmClubName(),
                farmClubInfo.farmClubImage(),
                farmClubInfo.wholeMemberCount(),
                steps,
                advice,
                farmClubInfo.daySinceStart());
    }

    public static List<Step> createSteps(List<StepVo> stepVoList, List<GetMissionPostListWithStepCountsAndImages> missionList) {
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
