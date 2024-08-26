package com.modernfarmer.farmusspring.domain.user.service;

import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.helper.MissionPostHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.DiaryCommentHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.DiaryLikeHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.MyVeggieHelper;
import com.modernfarmer.farmusspring.domain.user.dto.response.AlarmStatus;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;
import com.modernfarmer.farmusspring.domain.user.exception.custom.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final UserHelper userHelper;
    private final DiaryCommentHelper diaryCommentHelper;
    private final DiaryLikeHelper diaryLikeHelper;
    private final MyVeggieHelper myVeggieHelper;
    private final FarmClubHelper farmClubHelper;
    private final MissionPostHelper missionPostHelper;

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
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findUser(userId);
        // 유저 검증
        verifyUser(user);

        // 팜클럽 도메인 정보 삭제
            // 유저의 채소 엔티티 조회
            List<MyVeggie> myVeggieList = myVeggieHelper.getMyVeggieUserId(userId);
            // 내 채소 id를 통한 유저 팜클럽 삭제
            farmClubHelper.deleteFarmClubDomain(myVeggieList);

        // 채소 도메인 정보 삭제
            // 유저 id를 통한 내 채소 삭제
            myVeggieHelper.deleteMyVeggiesByUserId(userId);

        // 유저 도메인 정보 삭제
            // 1.1 유저 id를 통한 성장일기 좋아요 삭제
            diaryLikeHelper.deleteLikes(userId);
            // 1.2 유저 id를 통한 성장일기 댓글 삭제
            diaryCommentHelper.deleteComments(userId);
            // 1.3 유저 id를 통한 인증글 좋아요 삭제
            missionPostHelper.deleteMissionPostLikes(userId);
            // 1.4 유저 id를 통한 인증글 댓글 삭제
            missionPostHelper.deleteMissionPostComments(userId);

        // 1.0 유저 도메인 삭제
        userHelper.deleteUser(userId);
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
            throw  new UserNotFoundException("해당 유저가 존재하지 않습니다.",UserErrorCode.NOT_FOUND_USER);
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
    public void modifyNickname(Long userId, String nickname) {
       userRepository.updateNickname(userId, nickname);
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
        userRepository.updateNickname(userId, nickname);
    }

    private void updateProfileAndNickname(Long userId, String imageUrl, String nickname){
        userRepository.selectProfileAndNickname(userId,imageUrl,nickname);
    }

    public Optional<User> selectUser(Long userId){
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다.",UserErrorCode.NOT_FOUND_USER)));
        return user;
    }

    public void verifyUser(Optional<User> user){
        if(user.isEmpty())
            throw  new UserNotFoundException("유저가 존재하지 않습니다.", UserErrorCode.NOT_FOUND_USER);

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
