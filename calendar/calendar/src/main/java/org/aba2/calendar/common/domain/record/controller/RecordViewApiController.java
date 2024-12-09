package org.aba2.calendar.common.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.service.RecordService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordViewApiController {

    private final RecordService recordService;

    //일기 전체보기
    @GetMapping
    public String list(Model model,
                       @UserSession User user,
                       @RequestParam(value="page", defaultValue="0") int page) {

        if (page < 0) {
            throw new ApiException(RecordErrorCode.INVALID_PAGE_REQUEST, "페이지 번호는 0 이상이어야 합니다.");
        }

        Page<RecordEntity> paging = this.recordService.getRecordList(user.getId(), page);
        model.addAttribute("paging", paging);
        return "record/list";
    }
}