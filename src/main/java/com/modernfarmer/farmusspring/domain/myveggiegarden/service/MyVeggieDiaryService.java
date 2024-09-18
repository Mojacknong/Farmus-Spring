package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.UserFarmClubHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DiaryCommentReportDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DiaryDeleteDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DiaryReportDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.DiaryAccessDeniedException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.LikeNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.LikeAlreadyExistExcpetion;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.DiaryCommentHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.DiaryHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryCommentRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryLikeRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
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
    private final UserFarmClubHelper userFarmClubHelper;
    private final DiaryLikeRepository diaryLikeRepository;
    private final UserHelper userHelper;
    private final DiaryCommentHelper diaryCommentHelper;
    private final DiaryHelper diaryHelper;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggieDiary(
            MultipartFile multipartFile,
            String content,
            Boolean isOpen,
            String state,
            Long myVeggieId
    ) throws IOException {
        String imageUrl = getImageUrl(multipartFile);
        Optional<UserFarmClub> userFarmClub = userFarmClubHelper.findFarmClubByMyVeggieId(myVeggieId);
        log.info(String.valueOf(userFarmClub.map(UserFarmClub::getFarmClub).orElse(null)));

        userFarmClub.ifPresentOrElse(
                farmClub -> addMyyVeggieDiary(
                        content,
                        isOpen,
                        imageUrl,
                        state,
                        myVeggieId,
                        farmClub.getFarmClub()
                ),
                () -> addMyyVeggieDiary(
                        content,
                        isOpen,
                        imageUrl,
                        state,
                        myVeggieId,
                        null
                )
        );
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }
    @Transactional
    public BaseResponseDto<Void> reportDiary(DiaryReportDto diaryReportDto, Long userId) {
        User user = userHelper.getUserEntity(userId);
        Diary diary = diaryHelper.getDiaryEntity(diaryReportDto.getDiaryId());
        DiaryReport.createDiaryReport(diary,user, diaryReportDto.getReason());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @Transactional
    public BaseResponseDto<Void> reportDiaryComment(DiaryCommentReportDto diaryCommentReportDto, Long userId) {
        User user = userHelper.getUserEntity(userId);
        DiaryComment diaryComment = diaryCommentHelper.getDiaryCommentEntity(diaryCommentReportDto.getCommentId());
        DiaryCommentReport.createDiaryReport(diaryComment,user, diaryCommentReportDto.getReason());
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
        return proccessFarmClubData(diaryList);
    }
    private List<FarmClubDiary> proccessFarmClubData(List<SortedMyLikeDiary> diaryAllList){
        return diaryAllList.stream().map(allDiary -> {
            User user = allDiary.getDiary().getMyVeggie().getUser();
            return FarmClubDiary.of(
                    allDiary.getDiary(),
                    user,
                    DateManager.dotDateTime(allDiary.getDiary().getCreatedDate()),
                    allDiary.getDiary().getDiaryLikes().size(),
                    distinguishReportComment(allDiary.getDiary().getDiaryComments(), user.getId()),
                    allDiary.isMyLike(),
                    allDiary.isMyDiary(),
                    allDiary.getDiary().getState()
                    );}).toList();
    }

    private int distinguishReportComment(List<DiaryComment> diaryCommentList, Long userId) {
        return (int) diaryCommentList.stream()
                .filter(comment -> comment.getDiaryCommentReports().stream()
                        .noneMatch(report -> report.getUser().getId().equals(userId)))
                .count();
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
        Optional<DiaryLike> diaryLike = diaryLikeRepository.findDiaryLikeByDiaryIdAndUserId(diaryId, userId);
        checkLikeData(diaryLike);
        User userData = userService.selectUserById(userId);
        Diary diaryData = selectDiaryById(diaryId);
        insertLike(userData, diaryData);
    }
    public void checkLikeData(Optional<DiaryLike> diaryLike){
        if(diaryLike.isPresent()) {
            throw new LikeAlreadyExistExcpetion("좋아요 권한 에러", MyVeggieGardenErrorCode.EXIST_ALREADY_LIKE);
        }
    }
    @Transactional
    public void cancelLike(User user, Diary diary) {
        DiaryLike diaryLike = diaryRepository.findDiaryLikeByIdAndUser(user, diary);
        checkLikeDeleteData(diaryLike);
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
    public DiaryInteractionsDto selectComment(Long userId, Long diaryId)  {
        List<DiaryComment> diaryCommentList = diaryRepository.findDiaryByIdWithUserId(diaryId, userId);
        List<DiaryCommentContent> diaryCommentContent = DiaryCommentContent.processData(diaryCommentList, userId);
        int likeCount = diaryLikeRepository.findDiaryLikeCountById(diaryId);
        Optional<DiaryLike> diaryLike = diaryLikeRepository.findDiaryLikeByDiaryIdAndUserId(diaryId, userId);
        return DiaryInteractionsDto.of(diaryCommentContent,likeCount,diaryCommentList.size(), diaryLike.isPresent());
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
    public void checkLikeDeleteData(DiaryLike diaryLike){
        if(diaryLike == null) {
            throw new LikeNotFoundException("해당 좋아요 데이터는 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY_Like);
        }
    }
    public void validateDiaryComment(Optional<DiaryComment> diaryComment){
        if(diaryComment.isEmpty()){
            throw new DiaryCommentNotFoundException("해당 유저는 댓글 삭제 권한이 없습니다.");
        }
    }
    private void addMyyVeggieDiary(
            String content,
            Boolean isOpen,
            String image,
            String state,
            Long myVeggieId,
            FarmClub farmClub
    ){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(myVeggieId);
        Diary newDiary = Diary.createDiary(
                content,
                isOpen,
                image,
                state,
                myVeggie,
                farmClub
        );
        myVeggie.addDiary(newDiary);
    }
    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "dairyimage");
    }
}
