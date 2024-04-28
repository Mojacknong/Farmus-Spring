package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateMissionPostRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.CreateMissionPostResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.UserFarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class MissionPostService {

    private final MissionPostRepository missionPostRepository;

    private final UserFarmClubHelper userFarmClubHelper;

    private final S3Service s3Service;

    public CreateMissionPostResponseDto createMissionPost(CreateMissionPostRequestDto request, MultipartFile image) {
        UserFarmClub userFarmClub = userFarmClubHelper.getUserFarmClubEntity(request.userFarmClubId());
        String imageUrl = s3Service.uploadImage(image, "mission-post");
        Long missionPostId = saveMissionPost(request.toEntity(userFarmClub, imageUrl)).getId();
        return CreateMissionPostResponseDto.of(missionPostId);
    }

    private MissionPost saveMissionPost(MissionPost missionPost) {
        return missionPostRepository.save(missionPost);
    }
}
