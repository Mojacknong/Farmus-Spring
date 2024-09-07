package com.modernfarmer.farmusspring.domain.farmclub.helper;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetMissionPostCommentResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostComment;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostCommentRepository;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostVo;
import com.modernfarmer.farmusspring.domain.history.vo.MissionPostHistoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MissionPostHelper {

    private final MissionPostRepository missionPostRepository;
    private final MissionPostCommentRepository missionPostCommentRepository;

    public MissionPost getMissionPost(Long missionPostId) {
        return missionPostRepository.findById(missionPostId)
                .orElseThrow(() -> new FarmClubEntityNotFoundException("존재하지 않는 미션 게시글입니다.", FarmClubErrorCode.MISSION_POST_NOT_FOUND));
    }

    public MissionPostComment getMissionPostComment(Long missionPostCommentId) {
        return missionPostCommentRepository.findById(missionPostCommentId)
                .orElseThrow(() -> new FarmClubEntityNotFoundException("존재하지 않는 미션 게시글 댓글입니다.", FarmClubErrorCode.MISSION_POST_COMMENT_NOT_FOUND));
    }

    public List<MissionPostHistoryVo> getMissionPostHistory(Long userFarmClubId) {
        return missionPostRepository.getMissionPostHistory(userFarmClubId);
    }

    public List<MissionPostVo> getMissionPostList(Long userId, Long missionPostId) {
        return missionPostRepository.getMissionPostList(userId, missionPostId);
    }

    public GetMissionPostCommentResponseDto getMissionPostComments(Long missionPostId, Long userId) {
        return missionPostRepository.getMissionPostComment(missionPostId, userId);
    }

    @Transactional
    public void deleteMissionPostLike(Long userId, Long missionPostId) {
        missionPostRepository.deleteMissionPostLike(userId, missionPostId);
    }

    public void deleteMissionPost(MissionPost missionPost){
        missionPostRepository.delete(missionPost);
    }
    public void deleteMissionPostComment(MissionPostComment missionPostComment){
        missionPostCommentRepository.delete(missionPostComment);
    }
    public void deleteMissionPostComments(Long userId){
        missionPostRepository.deleteMissionPostComments(userId);
    }
    public void deleteMissionPostLikes(Long userId){
        missionPostRepository.deleteMissionPostLikes(userId);
    }
}
