package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;

import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyVeggieHelper {

    private final MyVeggieRepository myVeggieRepository;
}
