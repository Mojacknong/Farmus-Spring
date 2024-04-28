package com.modernfarmer.farmusspring.domain.veggieinfo.vo;

import java.util.List;

public record StepVo(
        int num,
        String content,
        List<String> tips
) {
}
