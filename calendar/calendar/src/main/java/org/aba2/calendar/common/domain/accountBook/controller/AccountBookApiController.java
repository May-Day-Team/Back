package org.aba2.calendar.common.domain.accountBook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accountbook")
@RequiredArgsConstructor
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    // 생성/수정-Post (여러 개의 AccountBook 저장)
    @PostMapping("/save")
    public Api<String> saveAccountBook(
            @UserSession User user,
            @RequestBody List<AccountBookFormRequest> formList
    ) {
        // 여러 개의 데이터를 처리하는 로직
        for (AccountBookFormRequest form : formList) {
            log.info("Save AccountBook date:{} userID:{}", form.getDate(), user.getId());
            accountBookService.handleAcctBookSaveOrUpdate(form, user.getId());
        }

        return Api.OK("가계부에 저장 완료");
    }

    // 삭제
    @DeleteMapping
    public Api<String> deleteAccountBook(@RequestBody AccountBookFormRequest form) {
        accountBookService.deleteAcctBook(form.getId());

        log.info("Deleting AccountBook createAt:{}", form.getDate());

        return Api.OK("삭제 완료");
    }
}