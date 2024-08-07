package com.modernfarmer.farmusspring.domain.veggieinfo.helper;

import com.modernfarmer.farmusspring.domain.veggieinfo.vo.VeggieInfoVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.exception.custom.VeggieInfoNotFoundException;
import com.modernfarmer.farmusspring.domain.veggieinfo.repository.VeggieInfoRepository;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VeggieInfoHelper {

    private final VeggieInfoRepository veggieInfoRepository;

    public VeggieInfo getVeggieInfoEntity(String veggieInfoId) {
        return veggieInfoRepository.findById(new ObjectId(veggieInfoId))
                .orElseThrow(() -> new VeggieInfoNotFoundException("존재하지 않는 채소 정보 아이디입니다."));
    }

    public VeggieInfo.Help getVeggieInfoHelp(String veggieInfoId) {
        VeggieInfo veggieInfo = veggieInfoRepository.findById(new ObjectId(veggieInfoId))
                .orElseThrow(() -> new VeggieInfoNotFoundException("존재하지 않는 채소 정보 아이디입니다."));

        return veggieInfo.getHelp();
    }

    public VeggieInfoVo getVeggieInfo(String veggieInfoId) {
        // Get veggie info for create farm club
        return veggieInfoRepository.getVeggieInfo(veggieInfoId);
    }

    public List<StepVo> getStepList(String veggieInfoId) {
        return veggieInfoRepository.getVeggieInfoStepList(veggieInfoId);
    }

    public String getStepName(String veggieInfoId, int step) {
        List<StepVo> stepList = getStepList(veggieInfoId);
        if (stepList.size() < step) {
            return "";
        }
        return stepList.get(step - 1).content();
    }
}
