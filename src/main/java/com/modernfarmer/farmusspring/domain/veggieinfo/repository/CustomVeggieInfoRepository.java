package com.modernfarmer.farmusspring.domain.veggieinfo.repository;

import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForRegister;

import java.util.List;

public interface CustomVeggieInfoRepository {

    public List<InfoForRegister> getVeggieInfoListForRegister();
}
