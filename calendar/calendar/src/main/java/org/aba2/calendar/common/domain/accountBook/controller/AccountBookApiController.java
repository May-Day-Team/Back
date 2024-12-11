package org.aba2.calendar.common.domain.accountBook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.accountBook.buisiness.AccountBookBusiness;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accountbook")
@RequiredArgsConstructor
public class AccountBookApiController {

    private final AccountBookBusiness accountBookBusiness;

    // 생성/수정-Post (여러 개의 AccountBook 저장)
    @PostMapping("/save")
    public Api<String> saveAccountBook(
            @UserSession User user,
            @RequestBody @Valid List<AccountBookFormRequest> formList
    ) {
        // 여러 개의 데이터를 처리하는 로직
        for (AccountBookFormRequest form : formList) {
            log.info("Save AccountBook date:{} userID:{}", form.getDate(), user.getId());
            accountBookBusiness.saveAccountBookResponse(user, form);
        }
        return Api.OK("AccountBook saved successfully");
    }

    // 삭제
    @DeleteMapping("/delete")
    public Api<String> deleteAccountBook(
            @UserSession User user,
            @RequestBody AccountBookFormRequest form
    ) {
        if (form.getId() == null) {
            throw new ApiException(RecordErrorCode.VALIDATION_ERROR, "해당 일기는 존재하지 않습니다");
        }

        log.info("Deleting AccountBook createAt:{}", form.getDate());

        var response = accountBookBusiness.deleteAccountBookResponse(user, form);

        return Api.OK("삭제 완료");
    }
}