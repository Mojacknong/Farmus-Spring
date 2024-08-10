package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DiaryDeleteDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.DiaryAccessDeniedException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.service.UserService;
import com.modernfarmer.farmusspring.domain.user.util.DateManager;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieDiaryService {

    private final S3Service s3Service;
    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;
    private final UserService userService;
    private final DiaryRepository diaryRepository;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggieDiary(
            MultipartFile multipartFile,
            String content,
            boolean isOpen,
            String state,
            Long myVeggieId
    ) throws IOException {
        String imageUrl = getImageUrl(multipartFile);
        addMyyVeggieDiary(
                content,
                isOpen,
                imageUrl,
                state,
                myVeggieId
        );
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @Transactional
    public BaseResponseDto<CheckTodayDiaryResponse> checkTodayDiary(MyVeggie myVeggie) {
        Diary diary = selectTodayDiary(myVeggie);
        boolean state = verifyDiaryState(diary);
        return BaseResponseDto.of(SuccessCode.SUCCESS,CheckTodayDiaryResponse.of(state));
    }

    @Transactional
    public void eraseDiary(DiaryDeleteDto diaryDeleteDto, Long userId){
        Optional<MyVeggie> myVeggie = myVeggieRepository.findMyVeggieByIdAndUserId((diaryDeleteDto.getMyVeggieId()), userId);
        verifyMyVeggie(myVeggie);
        Optional<Diary> diary = diaryRepository.findDiaryByIdAndMyVeggieId(diaryDeleteDto.getDiaryId(), myVeggie.get().getId());
        verifyDiary(diary);
        validateDiaryDelete(diary, diaryDeleteDto.getDiaryId());
        diaryRepository.deleteDiaryById(diaryDeleteDto.getDiaryId());
    }

    public void verifyDiary(Optional<Diary> diary){
        if(diary.isEmpty()){
            throw new DiaryNotFoundException("해당 일기는 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY);
        }
    }

    public void validateDiaryDelete(Optional<Diary> diary, Long diaryId){
        if(!diary.get().getId().equals(diaryId)){
            throw new DiaryAccessDeniedException("해당 일기 접근권한이 없습니다.", MyVeggieGardenErrorCode.NO_ACCESS_DIARY);
        }
    }

    public void verifyMyVeggie(Optional<MyVeggie> myVeggie){
        if(myVeggie.isEmpty()){
            throw new MyVeggieNotFoundException("존재하지 않는 채소입니다.",MyVeggieGardenErrorCode.NOT_FOUND_VEGGIE);
        }
    }


    @Transactional
    public List<FarmClubDiary> findDiaryAccordingToFarmClub(Long farmClubId, Long userId) {
        List<SortedMyLikeDiary> diaryList = diaryRepository.findDiaryByFarmClub(farmClubId, userId);
        List<FarmClubDiary> proccessData = proccessFarmClubData(diaryList);
        return proccessData;
    }

    private List<FarmClubDiary> proccessFarmClubData(List<SortedMyLikeDiary> diaryAllList){
        return diaryAllList.stream().map(allDiary -> {
            User user = allDiary.getDiary().getMyVeggie().getUser();
            return FarmClubDiary.of(
                    allDiary.getDiary(),
                    user,
                    DateManager.dotDateTime(allDiary.getDiary().getCreatedDate()),
                    allDiary.getDiary().getDiaryLikes().size(),
                    allDiary.getDiary().getDiaryComments().size(),
                    allDiary.isMyLike(),
                    allDiary.getDiary().getState()
                    );}).toList();
    }

    @Transactional
    public List<AllDairy> selectDiaryAll(MyVeggie myVeggie, Long userId) {
        List<SortedMyLikeDiary> diaryList = diaryRepository.findDiariesByMyVeggie(myVeggie, userId);
        return AllDairy.processData(diaryList);
    }

    @Transactional
    public MyVeggieDiaryCount selectDiaryCount(MyVeggie myVeggie) {
        List<Diary> diaryList = myVeggieRepository.findDiariesByMyVeggie(myVeggie);
        return MyVeggieDiaryCount.processData(diaryList);
    }

    @Transactional
    public void pressLike(Long userId, Long diaryId) {
        User userData = userService.selectUserById(userId);
        Diary diaryData = selectDiaryById(diaryId);
        insertLike(userData, diaryData);
    }

    @Transactional
    public void cancelLike(User user, Diary diary) {
        DiaryLike diaryLike = diaryRepository.findDiaryLikeByIdAndUser(user, diary);
        checkDiaryLikeData(diaryLike);
        deleteLike(user, diary);
    }

    @Transactional
    public void writeComment(Long userId, Long diaryId, String content) {
        User userData = userService.selectUserById(userId);
        Diary diaryData = selectDiaryById(diaryId);
        insertComment(content, userData, diaryData);
    }

    @Transactional
    public void deleteComment(User user, Long diaryCommentId) {
        Optional<DiaryComment> diaryCommentData = myVeggieRepository.findDiaryCommentByIdAndUserId(diaryCommentId, user);
        validateDiaryComment(diaryCommentData);
        myVeggieRepository.deleteDiaryCommentByIdAndUserId(diaryCommentId, user);
    }

    @Transactional
    public void updateComment(User user, Long diaryCommentId, String content) {

        Optional<DiaryComment> diaryCommentData = myVeggieRepository.findDiaryCommentByIdAndUserId(diaryCommentId, user);
        validateDiaryComment(diaryCommentData);
        myVeggieRepository.updateDiaryCommentByIdAndUserId(diaryCommentId, user, content);
    }
    @Transactional
    public BaseResponseDto<SelectDiaryOneResponse> selectDiaryOne(MyVeggie myVeggie)  {
        List<Diary> diaryList = diaryRepository.findDiaryByToday(myVeggie);
        if(diaryList.isEmpty()) {
            return BaseResponseDto.of(MyVeggieGardenSuccessCode.NOT_FOUND_DIARY, null);
        }
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                SelectDiaryOneResponse.of(
                        diaryList.get(0).getImage(),
                        diaryList.get(0).getContent(),
                        DateManager.formatDate(diaryList.get(0).getCreatedDate())
                ));
    }


    @Transactional
    public List<DiaryCommentContent> selectComment(Long userId, Long diaryId)  {
        List<DiaryComment> diaryCommentList = diaryRepository.findDiaryById(diaryId);
        List<DiaryCommentContent> diaryCommentContent = DiaryCommentContent.processData(diaryCommentList, userId);
        return diaryCommentContent;
    }



    public void insertComment(String content, User user, Diary diary){
        DiaryComment diaryComment = DiaryComment.createDiaryComment(content, diary, user);
        diary.addDiaryComment(diaryComment);
    }

    public void insertLike(User user, Diary diary){
        DiaryLike newDiary = DiaryLike.createDiaryLike(diary, user);
        diary.addDiaryLike(newDiary);
    }

    public void deleteLike(User user, Diary diary){
        diaryRepository.deleteDiaryLikeByIdAndUser(user, diary);
    }

    public boolean verifyDiaryState(Diary diary){
        if(diary == null){return true;}
        return  false;
    }

    public Diary selectTodayDiary(MyVeggie myVeggie){
        return myVeggieRepository.findDiariesByMyVeggieAndToday(myVeggie);
    }


    public Diary selectDiaryById(Long diaryId){
        Diary diaryData =  myVeggieRepository.findDiaryById(diaryId);
        checkDiaryData(diaryData);
        return diaryData;
    }

    public void checkDiaryData(Diary diary){
        if(diary == null) {
            throw new DiaryNotFoundException("해당 일기는 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY_Like);
        }
    }

    public void checkDiaryLikeData(DiaryLike diaryLike){
        if(diaryLike == null) {
            throw new DiaryLikeNotFoundException("해당 좋아요 데이터는 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY_Like);
        }
    }
    public void validateDiaryComment(Optional<DiaryComment> diaryComment){
        if(diaryComment.isEmpty()){
            throw new DiaryCommentNotFoundException("해당 유저는 댓글 삭제 권한이 없습니다.");
        }
    }

    private void addMyyVeggieDiary(
            String content,
            boolean isOpen,
            String image,
            String state,
            Long myVeggieId
    ){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(myVeggieId);
        Diary newDiary = Diary.createDiary(
                content,
                isOpen,
                image,
                state,
                myVeggie
        );
        myVeggie.addDiary(newDiary);
    }

    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "dairyimage");
    }
}
