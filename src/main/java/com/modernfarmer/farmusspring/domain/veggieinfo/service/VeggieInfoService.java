package com.modernfarmer.farmusspring.domain.veggieinfo.service;

import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.GetRecommendVeggieDto;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.GetVeggieListResponseDto;
import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;
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

    public GetVeggieListResponseDto getVeggieInfoListForRegister() {
        List<InfoForRegisterVo> veggieList = veggieInfoRepository.getVeggieInfoListForRegister();
        sortByDifficulty(veggieList);
        return GetVeggieListResponseDto.of(veggieList);
    }

    public String getFirstStepName(String veggieInfoId) {
        VeggieInfo veggieInfo = veggieInfoRepository.findById(new ObjectId(veggieInfoId)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채소 정보입니다."));

        return veggieInfo.getSteps().get(0).getContent();
    }

    public List<GetRecommendVeggieDto> getRecommendVeggieList(Long userid) {
        String difficulty = userHelper.getUserLevel(userid);
        return veggieInfoRepository.getRecommendVeggieList(difficulty);
    }

    private static void sortByDifficulty(List<InfoForRegisterVo> veggieList) {
        veggieList.sort((a, b) -> {
            if (a.difficulty().equals(Difficulty.EASY)) {
                return -1;
            } else if (a.difficulty().equals(Difficulty.NORMAL)) {
                if (b.difficulty().equals(Difficulty.EASY)) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        });
    }
}
