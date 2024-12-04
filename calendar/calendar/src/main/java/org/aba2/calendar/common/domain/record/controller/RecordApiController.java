package org.aba2.calendar.common.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.service.RecordService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordApiController {

    private final RecordService recordService;

    // 생성/수정-Post
    @PostMapping("/save")
    public void saveRecord(
            @UserSession User user,
            @RequestBody RecordFormRequest form) {

        recordService.handleRecordSaveOrUpdate(form, user.getId());
    }

    // 삭제
    @DeleteMapping
    public void deleteRecord(
            @UserSession User user,
            @RequestBody RecordFormRequest form) {

        recordService.deleteRecord(user.getId(), form.getCreateAt());
    }
}