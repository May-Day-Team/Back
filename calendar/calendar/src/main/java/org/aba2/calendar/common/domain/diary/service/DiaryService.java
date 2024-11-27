package org.aba2.calendar.common.domain.diary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.diary.db.DiaryRepository;
import org.aba2.calendar.common.domain.diary.model.DiaryRequest;
import org.aba2.calendar.common.domain.diary.model.DiaryEntity;
import org.aba2.calendar.common.domain.diary.model.DiaryId;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //일기 저장
    public DiaryEntity createDiary(DiaryRequest request, UserEntity user) {
        DiaryId diaryId = new DiaryId(LocalDate.now(), user.getUserId());

        //오늘 일기를 작성했는지 여부 확인
        if(diaryRepository.findByDiaryId(diaryId).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "오늘은 이미 일기를 작성하셨네요.");
        }
        DiaryEntity createDiary = DiaryEntity.builder()
                .diaryId(diaryId)
                .title(request.getTitle())
                .content(request.getContent())
                .tag(request.getTag())
                .weather(request.getWeather())
                .createAt(LocalDate.now())
                .user(user)
                .build();

        return diaryRepository.save(createDiary);
    }

    //일기 수정
    @Transactional
    public void updateDiary(DiaryRequest request, DiaryId diaryId) {
        // DiaryId를 기준으로 일기 찾기
        DiaryEntity existingDiary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 일기는 존재하지 않습니다."));
        // TODO 일기 ERROR CODE 만들기

        //기존 일기의 필드 수정 - setter, save 없음
        existingDiary.updateDiary(
                request.getTitle(),
                request.getContent(),
                request.getTag(),
                request.getWeather()
        );
    }

    //일기 삭제
    public void deleteDiary(DiaryId diaryId){
        // DiaryId를 기준으로 일기 찾기
        DiaryEntity existingDiary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 일기는 존재하지 않습니다."));
        diaryRepository.delete(existingDiary);
    }

    //일기 리스트 불러오기
    public List<DiaryEntity> diaryList(String userId) {
        return diaryRepository.findByUser_UserId(userId);
    }
}
