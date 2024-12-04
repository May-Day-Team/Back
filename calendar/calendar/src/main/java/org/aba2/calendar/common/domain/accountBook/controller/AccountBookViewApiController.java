package org.aba2.calendar.common.domain.accountBook.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/acctBk")
@RequiredArgsConstructor
public class AccountBookViewApiController {

    private final AccountBookService accountBookService;

    //가계부 전체보기
    @GetMapping
    public String list(Model model,
                       @UserSession User user,
                       @RequestParam(value="page", defaultValue="0") int page) {

        Page<AccountBookEntity> paging = this.accountBookService.findAllByUIDAcctBookList(user.getId(), page);
        model.addAttribute("paging", paging);
        return "acctBook/list";
    }
}