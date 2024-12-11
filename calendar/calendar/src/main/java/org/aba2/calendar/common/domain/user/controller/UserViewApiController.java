package org.aba2.calendar.common.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class UserViewApiController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/join")
    public String join() {
        return "login/join";
    }

    @GetMapping("/mayday")
    public String mayday() {
        return "mainpage/mayday";
    }
    @GetMapping("/calendar")
    public String calendar() {
        return "mainpage/calendarpage";
    }
    @GetMapping("/group")
    public String group() {
        return "group/group";
    }
    @GetMapping("/accountbook")
    public String accountbook() {
        return "accountbook/accountbook";
    }
    @GetMapping("/record")
    public String record() {
        return "record/record";
    }
    @GetMapping("/addschedule")
    public String addschedule() {
        return "Calendar/addschedule";
    }
    @GetMapping("/index")
    public String index() {
        return "Calendar/index";
    }
    @GetMapping("/month")
    public String month() {
        return "Calendar/month";
    }
    @GetMapping("/week")
    public String week() {
        return "Calendar/week";
    }
    @GetMapping("/day")
    public String day() {
        return "Calendar/day";
    }


}

