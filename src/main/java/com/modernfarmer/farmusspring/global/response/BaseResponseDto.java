package com.modernfarmer.farmusspring.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponseDto<T> {
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> BaseResponseDto of(ResponseCode message, T data){

        return BaseResponseDto.builder()
                .code(message.getCode())
                .message(message.getMessage())
                .data(data)
                .build();
    }
}