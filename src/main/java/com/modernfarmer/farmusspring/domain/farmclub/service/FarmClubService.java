package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.CreateFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmClubService {

    private final FarmClubRepository farmClubRepository;

    public CreateFarmClubResponseDto createFarmClub(CreateFarmClubRequestDto request) {
        // 몽고에서 이미지, 난이도 가져오기

        return null;
    }

    public List<SearchFarmClubResponseDto> searchFarmClub(String keyword) {
        // 몽고에서 이미지, 난이도 가져오기

        return null;
    }
}
