package com.modernfarmer.farmusspring.domain.user.service;

import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public BaseResponseDto<UserProfileResponse> selectUserProfile(Long userId) {

        Optional<User> userData = selectUser(userId);

        long dDay = calFromToday(userData.get().getCreatedDate());

        return BaseResponseDto.of(SuccessCode.SUCCESS,
                UserProfileResponse.of(
                        userData.get().getNickname(),
                        userData.get().getProfileImage(),
                        dDay
        ));
    }

    @Transactional
    public BaseResponseDto<Void> deleteProfleImage(Long userId) {

        updateProfileImage(userId);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }


    private Optional<User> selectUser(Long userId){
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다.")));
        return user;
    }
    private long calFromToday(LocalDateTime date){

        LocalDateTime currentDateTime = LocalDateTime.now();
        long daysDifference = ChronoUnit.DAYS.between(date.toLocalDate(), currentDateTime.toLocalDate());
        return daysDifference;
    }

    private void updateProfileImage(Long userId){
        userRepository.updateProfileImage(userId);
    }


}
