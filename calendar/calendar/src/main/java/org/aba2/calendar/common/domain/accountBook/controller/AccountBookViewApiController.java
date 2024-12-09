package org.aba2.calendar.common.domain.accountBook.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.accountBook.dto.DateTotalDTO;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/acctBk")
@RequiredArgsConstructor
public class AccountBookViewApiController {

    private final AccountBookService accountBookService;

    //가계부 전체보기-특정 날짜의 손익 합산
    @GetMapping
    public String list(Model model,
                       @UserSession User user,
                       @RequestParam(value="page", defaultValue="0") int page) {

        Page<DateTotalDTO> dateTotals = accountBookService.getPagedDateTotals(user.getId(), page);

        model.addAttribute("paging", dateTotals);

        return "acctBook/list";
    }

    //해당 날짜의 가계부 전부 보기
    @GetMapping("/{date}")
    public String detail(Model model,
                       @UserSession User user,
                       @PathVariable LocalDate date){
        List<AccountBookEntity> list = accountBookService.getUIDAndDateWithThrow(user.getId(), date);

        model.addAttribute("list", list);

        return "acctBook/detail";
    }
}