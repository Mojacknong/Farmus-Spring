package com.modernfarmer.farmusspring.domain.veggieinfo.repository;

import com.modernfarmer.farmusspring.domain.veggieinfo.vo.CreateFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.InfoForRegisterVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;

import java.util.List;

public interface CustomVeggieInfoRepository {

    List<InfoForRegisterVo> getVeggieInfoListForRegister();
    CreateFarmClubVo getVeggieInfoForCreateFarmClub(String veggieInfoId);
    List<StepVo> getVeggieInfoStepList(String veggieInfoId);
}
