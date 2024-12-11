package org.aba2.calendar.common.domain.record.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.record.business.RecordBusiness;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordApiController {

    private final RecordBusiness recordBusiness;

    @GetMapping
    public Api<List<RecordResponse>> getRecordList(
            @UserSession User user
    ) {
        return Api.OK(recordBusiness.getRecordList(user.getId()));
    }


    // 생성/수정-Post
    @PostMapping("/save")
    public Api<RecordResponse> saveRecord(
            @UserSession User user,
            @RequestBody @Valid RecordFormRequest form
    ) {

        log.info("Create/Update record createAt:{} userId{}", form.getCreateAt(), user.getId());

        var response = recordBusiness.saveRecordResponse(user, form);

        return Api.OK(response);
    }

    // 삭제
    @DeleteMapping("/delete")
    public Api<String> deleteRecord(
            @UserSession User user,
            @RequestBody RecordFormRequest form
    ) {
        if (form.getCreateAt() == null) {
            throw new ApiException(RecordErrorCode.VALIDATION_ERROR, "해당 일기는 존재하지 않습니다");
        }

        log.info("Deleting Record createAt:{} userId{}", form.getCreateAt(),user.getId());

        var response = recordBusiness.deleteRecordResponse(user, form);

        return Api.OK(response);
    }
}