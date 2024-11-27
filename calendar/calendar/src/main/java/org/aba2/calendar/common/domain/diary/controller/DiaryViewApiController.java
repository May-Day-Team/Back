package org.aba2.calendar.common.domain.diary.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.diary.model.DiaryEntity;
import org.aba2.calendar.common.domain.diary.service.DiaryService;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryViewApiController {

    private final DiaryService diaryService;

    //나중에 페이지 만들어야할듯함
    @GetMapping("/list")
    public String diaryList(Model model, User user){
        List<DiaryEntity> list =  diaryService.diaryList(user.getId());
        model.addAttribute(list);
        return "diary/diaryList";
    }

    @GetMapping("/create")
    public String create() { return "diary/create"; }
    
    @PostMapping("/create")
    public String create(@ModelAttribute DiaryEntity diary) {

        return "다이어리 리스트";
    }

    @GetMapping("/update")
    public String update() { return "diary/update"; }

    @PostMapping("/update")
    public String update(@ModelAttribute DiaryEntity diary) {

        return "다이어리 리스트";
    }

    @PostMapping("/delete")
    public String delete() { 
        return "id만 받아서 삭제하고 리스트로 복귀";
    }
}
/**
 * 처음에 나오는 화면은 리스트
 */