package com.modernfarmer.farmusspring.domain.user.service;


import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetMotivationRequest;
import com.modernfarmer.farmusspring.domain.user.dto.response.SetLevelResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.entity.UserMotivation;
import com.modernfarmer.farmusspring.domain.user.repository.UserMotivationRepository;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class OnBoardingService {

    private final UserMotivationRepository userMotivationRepository;
    private final UserRepository userRepository;

    @Transactional
    public BaseResponseDto<Void> settingMotiavation(User user, SetMotivationRequest setMotivationRequest) {

        insertMotivation(user, setMotivationRequest);

        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }
    @Transactional
    public BaseResponseDto<SetLevelResponse> settingLevel(Long userId, SetLevelRequest setLevelRequest){

        String level = measureLevel(setLevelRequest.getTime(), setLevelRequest.getSkill());

        insertLevel(userId, level);

        return BaseResponseDto.of(SuccessCode.SUCCESS, SetLevelResponse.of(level));

    }
    @Transactional
    public BaseResponseDto<Void> completeOnBoarding(Long userId)  {

        updateCompleteBoarding(userId);

        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }


    private void updateCompleteBoarding(Long userId){
        userRepository.updateEarly(userId);
    }


    public void insertMotivation(User user, SetMotivationRequest setMotivationRequest) {

        List<UserMotivation> userMotivations = new ArrayList<>();
        for (String motivation : setMotivationRequest.getMotivation()) {

            UserMotivation userMotivation = UserMotivation.createUserMotivation(motivation, user);
            userMotivations.add(userMotivation);
        //    userMotivationRepository.insertMotivation(motivation, user.getId());
    }
        userMotivationRepository.saveAll(userMotivations);
    }

    private void insertLevel(Long userId, String level){
        userRepository.insertUserLevel(userId, level);
    }


    private String  measureLevel(int time,String skill) {
        boolean isIntermediate = false;
        boolean isMaster = false;
        boolean isElementary = false;
        boolean isBeginner = false;


        if ("홈파밍 중급".equals(skill)) {
            isIntermediate = true;
        } else if ("홈파밍 고수".equals(skill)) {
            isMaster = true;
        } else if ("홈파밍 초보".equals(skill)) {
            isElementary = true;
        } else if ("홈파밍 입문".equals(skill)) {
            isBeginner = true;
        }


        if (time == 2 && (isIntermediate || isMaster)) {
            return "HARD";
        } else if (time == 2 && (isBeginner || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isMaster) {
            return "HARD";
        } else if (time == 1 && (isIntermediate || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isBeginner) {
            return "EASY";
        } else if (time == 0 && isMaster) {
            return "HARD";
        } else if (time == 0 && isIntermediate) {
            return "NORMAL";
        } else if (time == 0 && (isElementary || isBeginner)) {
            return "EASY";
        }

        return "알 수 없음";
    }



}
