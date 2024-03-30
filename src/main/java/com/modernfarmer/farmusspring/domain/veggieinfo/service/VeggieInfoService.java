package com.modernfarmer.farmusspring.domain.veggieinfo.service;

import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForRegister;
import com.modernfarmer.farmusspring.domain.veggieinfo.repository.VeggieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeggieInfoService {

    private final VeggieInfoRepository veggieInfoRepository;

    public List<InfoForRegister> getVeggieInfoListForRegister() {
        // Get all veggie info list for register
        return veggieInfoRepository.getVeggieInfoListForRegister();
    }
}
