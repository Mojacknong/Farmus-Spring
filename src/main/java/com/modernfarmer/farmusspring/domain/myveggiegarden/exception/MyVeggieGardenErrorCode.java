package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyVeggieGardenErrorCode implements ResponseCode {

    NOT_FOUND_ROUTINE(3000, "해당 루틴을 찾을 수 없습니다."),
    NOT_FOUND_DIARY(3001, "해당 일기를 찾을 수 없습니다."),
    NOT_FOUND_DIARY_COMMENT(3002, "해당 유저 권한의 일기를 찾을 수 없습니다"),
    NOT_FOUND_DIARY_Like(3003, "해당 유저 권한의 좋아요를 찾을 수 없습니다."),
    NOT_FOUND_VEGGIE(3004, "해당 채소를 찾을 수 없습니다."),
    NO_VEGGIE_FOR_REGISTER(3005, "해당 팜클럽에 가입할 수 있는 채소가 없습니다.");
    private final int code;
    private final String message;
}
