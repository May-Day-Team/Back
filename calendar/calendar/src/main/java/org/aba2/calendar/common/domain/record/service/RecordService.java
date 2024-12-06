package org.aba2.calendar.common.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.record.db.RecordRepository;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.model.RecordId;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;

    // 일기 리스트 조회 - 페이지
    public Page<RecordEntity> getRecordList(String userId, int page) {
        Pageable pageable = PageRequest.of(page, 10); // 페이지네이션만 설정
        return recordRepository.findAllByRecordId_UserIdOrderByRecordId_CreateAtDesc(userId, pageable);
    }

    // 생성/수정 핸들
    @Transactional
    public void handleRecordSaveOrUpdate(RecordFormRequest request, String userId) {
        if (recordRepository.findByRecordId(new RecordId(request.getCreateAt(), userId)).isPresent()) {
            updateRecord(request, userId);
        } else {
            createRecord(request, userId);
        }
    }

    // 생성하기
    private void createRecord(RecordFormRequest request, String userId) {
        RecordId recordId = new RecordId(request.getCreateAt(), userId);

        // user객체 넣어줘야함
        UserEntity user = userService.findByIdWithThrow(userId);

        // 기존 다이어리 존재 여부 확인
        RecordEntity newRecord = RecordEntity.builder()
                .recordId(recordId)
                .title(request.getTitle())
                .content(request.getContent())
                .weather(request.getWeather())
                .user(user)
                .build();
        recordRepository.save(newRecord);
    }

    // 수정하기
    private void updateRecord(RecordFormRequest request, String userId) {
        // 기존 다이어리 존재 여부 확인
        RecordEntity record = findByRecordIdWithThrow(userId, request.getCreateAt());

        //기존 일기의 필드 수정 - dirtyCheck
        record.updateRecord(
                request.getTitle(),
                request.getContent(),
                request.getWeather()
        );
    }

    // 일기 삭제
    public void deleteRecord(String userId, LocalDate date){
        recordRepository.delete(findByRecordIdWithThrow(userId, date));
    }

    // 일기 찾기
    public RecordEntity findByRecordIdWithThrow(String userId, LocalDate date) {
        return recordRepository.findByRecordId(new RecordId(date, userId))
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 일기는 존재하지 않습니다."));
    }
}
