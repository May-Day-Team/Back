package org.aba2.calendar.common.domain.record.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.service.RecordConverter;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.record.service.RecordService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class RecordBusiness {

    private final RecordService recordService;

    private final RecordConverter recordConverter;

    // 서비스에서 데이터를 가져와서 변환
//    public Page<RecordResponse> getRecordList(String userId, int page) {
//        Page<RecordEntity> records = recordService.getRecordList(userId, page); // Service 호출
//        // 변환 로직을 RecordConverter로 위임
//        return records.map(recordConverter::toResponse); // 변환 로직
//    }

    // 서비스에서 데이터를 가져와서 변환
    public List<RecordResponse> getRecordList(String userId) {
        // 서비스에서 레코드 목록을 가져
        List<RecordEntity> records = recordService.getRecordList(userId);

        // 변환 로직을 RecordConverter로 위임하여 리스트 형식으로 변환
        return records.stream()
                .map(recordConverter::toResponse) // RecordEntity를 RecordResponse로 변환
                .collect(Collectors.toList()); // 리스트로 수집
    }

    // 일기 작성,수정
    public RecordResponse saveRecordResponse(User user, RecordFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var response = recordService.handleRecordSaveOrUpdate(request, user.getId());

        return recordConverter.toResponse(response);
    }

    // 일기 삭제
    public String deleteRecordResponse(User user, RecordFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        recordService.deleteRecord(user.getId(), request.getCreateAt());

        return "Record deleted successfully";
    }
}
