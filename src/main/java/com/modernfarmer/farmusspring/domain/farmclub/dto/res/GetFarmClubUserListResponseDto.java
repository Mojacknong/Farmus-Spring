package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.GetFarmClubUserVo;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetFarmClubUserListResponseDto(
        List<GetFarmClubUserVo> userList
) {
    public static GetFarmClubUserListResponseDto of(List<GetFarmClubUserVo> userList) {
        return GetFarmClubUserListResponseDto.builder()
                .userList(userList)
                .build();
    }
}
