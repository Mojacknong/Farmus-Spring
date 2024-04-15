package com.modernfarmer.farmusspring.domain.farmclub.helper;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FarmClubHelper {

    private final FarmClubRepository farmClubRepository;

    public FarmClub getFarmClubEntity(Long id) {
        return farmClubRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팜클럽입니다."));
    }
}
