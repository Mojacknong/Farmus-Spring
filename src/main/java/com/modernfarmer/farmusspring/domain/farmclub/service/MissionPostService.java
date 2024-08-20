package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateMissionPostCommentRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateMissionPostRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.*;
import com.modernfarmer.farmusspring.domain.farmclub.helper.MissionPostHelper;
import com.modernfarmer.farmusspring.domain.farmclub.helper.UserFarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostCommentVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostVo;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
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
    private final VeggieInfoHelper veggieInfoHelper;

    private final S3Service s3Service;

    @Transactional
    public CreateMissionPostResponseDto createMissionPost(Long userId, CreateMissionPostRequestDto request, MultipartFile image) {
        UserFarmClub userFarmClub = userFarmClubHelper.findByUserIdAndFarmClubId(userId, request.farmClubId());
        String imageUrl = s3Service.uploadImage(image, "mission-post");
        MissionPost missionPost = saveMissionPost(request.toEntity(userFarmClub, imageUrl, userFarmClub.getCurrentStep()));
        userFarmClub.addMissionPost(missionPost);
        userFarmClub.updateStep(veggieInfoHelper.getStepName(userFarmClub.getFarmClub().getVeggieInfoId(), userFarmClub.getCurrentStep() + 1));
        return CreateMissionPostResponseDto.of(missionPost.getId());
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

    public void deleteMissionPostLike(Long userId, Long missionPostId) {
        missionPostHelper.deleteMissionPostLike(userId, missionPostId);
    }

    public GetMissionPostListResponseDto getMissionPostList(Long userId, Long farmClubId) {
        List<MissionPostVo> missionPosts = missionPostHelper.getMissionPostList(userId, farmClubId);
        return GetMissionPostListResponseDto.of(missionPosts);
    }

    public GetMissionPostCommentResponseDto getMissionPostComment(Long missionPostId, Long userId) {
        List<MissionPostCommentVo> comments = missionPostRepository.getMissionPostComment(missionPostId, userId);
        return GetMissionPostCommentResponseDto.of(comments);
    }

    private MissionPost saveMissionPost(MissionPost missionPost) {
        return missionPostRepository.save(missionPost);
    }
}
