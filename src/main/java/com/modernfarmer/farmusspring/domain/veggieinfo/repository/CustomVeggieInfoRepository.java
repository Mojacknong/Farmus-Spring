package com.modernfarmer.farmusspring.domain.veggieinfo.repository;

import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.GetRecommendVeggieDto;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.VeggieInfoVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.InfoForRegisterVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;

import java.util.List;

public interface CustomVeggieInfoRepository {

    List<InfoForRegisterVo> getVeggieInfoListForRegister();
    VeggieInfoVo getVeggieInfo(String veggieInfoId);
    List<StepVo> getVeggieInfoStepList(String veggieInfoId);
    List<GetRecommendVeggieDto> getRecommendVeggieList(String difficulty);
}
