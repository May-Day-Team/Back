package org.aba2.calendar.common.domain.accountBook.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acctBk")
@RequiredArgsConstructor
public class AccountBookApiController {

    private final AccountBookService accountBookService;

    // 생성/수정-Post
    @PostMapping("/save")
    public void saveAccountBook(
                                  @UserSession User user,
                                  @RequestBody AccountBookFormRequest form) {

        accountBookService.handleAcctBookSaveOrUpdate(form, user.getId());
    }

    // 삭제
    @DeleteMapping
    public void deleteAccountBook(Long id) {
        accountBookService.deleteAcctBook(id);
    }
}