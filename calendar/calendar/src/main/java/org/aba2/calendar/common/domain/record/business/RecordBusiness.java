package org.aba2.calendar.common.domain.record.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.record.service.RecordConverter;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.record.service.RecordService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Objects;

@Business
@RequiredArgsConstructor
public class RecordBusiness {

    private final RecordService recordService;

    private final RecordConverter recordConverter;

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
