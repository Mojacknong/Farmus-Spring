package com.modernfarmer.farmusspring.domain.veggieinfo.repository;

import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.CreateFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForRegister;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;

import java.util.List;

public interface CustomVeggieInfoRepository {

    List<InfoForRegister> getVeggieInfoListForRegister();
    CreateFarmClubVo getVeggieInfoForCreateFarmClub(String veggieInfoId);
    List<StepVo> getVeggieInfoStepList(String veggieInfoId);
}
