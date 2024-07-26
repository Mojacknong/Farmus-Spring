package com.modernfarmer.farmusspring.domain.user.service;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.DiaryNotFoundException;
import com.modernfarmer.farmusspring.domain.user.dto.response.AlarmStatus;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Config;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

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

    @Transactional
    public BaseResponseDto<Void> settingProfile(
            Long userId,
            MultipartFile multipartFile,
            String nickName
    ) throws IOException {

        updateUserProfileAccordingToProfileImage(multipartFile, nickName, userId);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);

    }



    public User selectUserById(Long userId){
        User user = userRepository.findUserData(userId);
        checkUserData(user);
        return user;
    }


    public void checkUserData(User user){
        if(user == null) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void initUser(Long userId) {
        User user = userRepository.findUserById(userId);
        user.initUser();
    }


    @Transactional
    public void modifyNotification(Long userId, Boolean status) {
        userRepository.updateNotification(userId, status);
    }

    @Transactional
    public AlarmStatus bringNotification(Long userId) {
        User user = selectUserById(userId);
        return AlarmStatus.of(user.getNotificationStatus());
    }



    private void updateUserProfileAccordingToProfileImage(MultipartFile multipartFile, String nickName, Long userId) throws IOException {
        if(multipartFile.isEmpty()){

            updateNickname(nickName, userId);
        }else{

            String imageUrl = getImageUrl(multipartFile);
            updateProfileAndNickname(userId, imageUrl, nickName);
        }
    }

    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "userprofileimage");
    }

    private void updateNickname(String nickname, Long userId){
        userRepository.updateUserNickname(nickname,userId);
    }

    private void updateProfileAndNickname(Long userId, String imageUrl, String nickname){
        userRepository.selectProfileAndNickname(userId,imageUrl,nickname);
    }

    public Optional<User> selectUser(Long userId){
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
