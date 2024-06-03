package com.modernfarmer.farmusspring.domain.veggieinfo.service;

import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.GetRecommendVeggieDto;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.InfoForRegisterVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.repository.VeggieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeggieInfoService {

    private final VeggieInfoRepository veggieInfoRepository;
    private final UserHelper userHelper;

    public List<InfoForRegisterVo> getVeggieInfoListForRegister() {
        // Get all veggie info list for register
        return veggieInfoRepository.getVeggieInfoListForRegister();
    }

    public String getFirstStepName(String veggieInfoId) {
        VeggieInfo veggieInfo = veggieInfoRepository.findById(new ObjectId(veggieInfoId)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채소 정보입니다."));

        return veggieInfo.getSteps().get(0).getContent();
    }

    public List<GetRecommendVeggieDto> getRecommendVeggieList(Long userid) {
        String difficulty = userHelper.getUserLevel(userid);
        return veggieInfoRepository.getRecommendVeggieList(difficulty);
    }
}
