package org.aba2.calendar.common.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.record.db.RecordRepository;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.model.RecordId;
import org.aba2.calendar.common.domain.record.model.enums.Weather;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;

    // 일기 리스트 조회 - 페이지
//    public Page<RecordEntity> getRecordList(String userId, int page) {
//        Pageable pageable = PageRequest.of(page, 10);
//        return recordRepository.findAllByRecordId_UserIdOrderByRecordId_CreateAtDesc(userId, pageable);
//    }

    public List<RecordEntity> getRecordList(String userId){
        return recordRepository.findAllByRecordId_UserIdOrderByRecordId_CreateAtDesc(userId)
                .orElseThrow(() -> new ApiException(RecordErrorCode.RECORD_NOT_FOUND, "일기 목록이 없습니다"));
    }

    // 생성/수정 핸들
    @Transactional
    public RecordEntity handleRecordSaveOrUpdate(RecordFormRequest request, String userId) {
        if (recordRepository.existsByRecordId(new RecordId(request.getCreateAt(), userId))) {
            return updateRecord(request, userId);
        } else {
            return createRecord(request, userId);
        }
    }

    // 생성하기
    private RecordEntity createRecord(RecordFormRequest request, String userId) {
        RecordId recordId = new RecordId(request.getCreateAt(), userId);

        // user객체 넣어줘야함
        UserEntity user = userService.findByIdWithThrow(userId);

        // 문자열로 받은걸 Weather ENUM으로 변환
        Weather weather = Weather.get(request.getWeather());

        RecordEntity newRecord = RecordEntity.builder()
                .recordId(recordId)
                .title(request.getTitle())
                .content(request.getContent())
                .weather(weather)
                .updateAt(request.getCreateAt())
                .user(user)
                .build();
        return recordRepository.save(newRecord);
    }

    // 수정하기
    private RecordEntity updateRecord(RecordFormRequest request, String userId) {
        // 기존 다이어리 존재 여부 확인
        RecordEntity record = findByRecordIdWithThrow(userId, request.getCreateAt());

        // 일기 수정 권한(작성자) 여부 확인
        validateUserAuthorization(userId, record);

        // 문자열로 받은걸 Weather ENUM으로 변환
        Weather weather = Weather.get(request.getWeather());

        //기존 일기의 필드 수정 - dirtyCheck
        record.updateRecord(
                request.getTitle(),
                request.getContent(),
                weather
        );
        return record;
    }

    // 일기 삭제
    public void deleteRecord(String userId, LocalDate date){
        // 기존 다이어리 존재 여부 확인
        RecordEntity record = findByRecordIdWithThrow(userId, date);

        // 일기 삭제 권한(작성자) 여부 확인
        validateUserAuthorization(userId, record);

        // 삭제
        recordRepository.delete(record);
    }

    // 일기 찾기
    public RecordEntity findByRecordIdWithThrow(String userId, LocalDate date) {
        return recordRepository.findByRecordId(new RecordId(date, userId))
                .orElseThrow(() -> new ApiException(RecordErrorCode.RECORD_NOT_FOUND, "해당 일기를 찾을 수 없습니다"));
    }
    
    // 권한 조회
    private void validateUserAuthorization(String userId, RecordEntity record) {
        if (!record.getUser().getUserId().equals(userId)) {
            throw new ApiException(RecordErrorCode.UNAUTHORIZED_ACCESS, "권한이 없습니다.");
        }
    }
}
