package com.modernfarmer.farmusspring.domain.farmclub.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FarmClubErrorCode implements ResponseCode {

    FARM_CLUB_NOT_FOUND(3001, "해당 팜클럽을 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
