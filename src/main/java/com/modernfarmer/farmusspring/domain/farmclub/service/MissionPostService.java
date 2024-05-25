package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateMissionPostCommentRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateMissionPostRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostComment;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostLike;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.MissionPostHelper;
import com.modernfarmer.farmusspring.domain.farmclub.helper.UserFarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostCommentVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostVo;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MissionPostService {

    private final MissionPostRepository missionPostRepository;

    private final UserFarmClubHelper userFarmClubHelper;
    private final MissionPostHelper missionPostHelper;
    private final UserHelper userHelper;

    private final S3Service s3Service;

    public CreateMissionPostResponseDto createMissionPost(CreateMissionPostRequestDto request, MultipartFile image) {
        UserFarmClub userFarmClub = userFarmClubHelper.getUserFarmClubEntity(request.userFarmClubId());
        String imageUrl = s3Service.uploadImage(image, "mission-post");
        Long missionPostId = saveMissionPost(request.toEntity(userFarmClub, imageUrl)).getId();
        return CreateMissionPostResponseDto.of(missionPostId);
    }

    @Transactional
    public CreateMissionPostCommentResponseDto createMissionPostComment(Long userId, CreateMissionPostCommentRequestDto requestDto) {
        MissionPost missionPost = missionPostHelper.getMissionPost(requestDto.missionPostId());
        User user = userHelper.getUserEntity(userId);
        MissionPostComment.createMissionPostComment(requestDto.content(), missionPost, user);
        return CreateMissionPostCommentResponseDto.of(missionPost.getId());
    }

    @Transactional
    public CreateMissionPostLikeResponseDto createMissionPostLike(Long userId, Long missionPostId) {
        MissionPost missionPost = missionPostHelper.getMissionPost(missionPostId);
        User user = userHelper.getUserEntity(userId);
        MissionPostLike.createMissionPostLike(missionPost, user);
        return CreateMissionPostLikeResponseDto.of(missionPost.getId());
    }

    public GetMissionPostListResponseDto getMissionPostList(Long farmClubId) {
        List<MissionPostVo> missionPosts = missionPostRepository.getMissionPostList(farmClubId);
        return GetMissionPostListResponseDto.of(missionPosts);
    }

    public GetMissionPostCommentResponseDto getMissionPostComment(Long missionPostId) {
        List<MissionPostCommentVo> comments = missionPostRepository.getMissionPostComment(missionPostId);
        return GetMissionPostCommentResponseDto.of(comments);
    }

    private MissionPost saveMissionPost(MissionPost missionPost) {
        return missionPostRepository.save(missionPost);
    }
}
