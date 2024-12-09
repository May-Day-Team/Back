package org.aba2.calendar.common.domain.accountBook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/acctBk")
@RequiredArgsConstructor
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    // 생성/수정-Post (여러 개의 AccountBook 저장)
    @PostMapping("/save")
    public ResponseEntity<Api<Void>> saveAccountBook(
            @UserSession User user,
            @RequestBody List<AccountBookFormRequest> formList
    ) {
        // 여러 개의 데이터를 처리하는 로직
        for (AccountBookFormRequest form : formList) {
            log.info("Save AccountBook date:{} userID:{}", form.getDate(), user.getId());
            accountBookService.handleAcctBookSaveOrUpdate(form, user.getId());
        }
        return ResponseEntity.ok(null);
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<Api<Void>> deleteAccountBook(@RequestBody AccountBookFormRequest form) {
        accountBookService.deleteAcctBook(form.getAccountBookId());

        log.info("Deleting AccountBook createAt:{}", form.getDate());

        return ResponseEntity.ok(null);
    }
}