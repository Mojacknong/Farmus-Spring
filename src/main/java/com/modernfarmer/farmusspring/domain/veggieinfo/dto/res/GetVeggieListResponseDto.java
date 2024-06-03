package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.vo.InfoForRegisterVo;
import lombok.Builder;

import java.util.List;

@Builder
public record GetVeggieListResponseDto(
        List<InfoForRegisterVo> veggieList
) {
    public static GetVeggieListResponseDto of(List<InfoForRegisterVo> veggieList) {
        return GetVeggieListResponseDto.builder()
                .veggieList(veggieList)
                .build();
    }
}
