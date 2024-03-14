package com.modernfarmer.farmusspring.domain.test.service;

import com.modernfarmer.farmusspring.domain.test.exception.TestException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    public String test(Boolean flag) {
        if (flag)
            throw new TestException("test");
        else
            return "test";
    }
}
