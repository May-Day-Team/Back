package org.aba2.calendar.common.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class UserViewApiController {

    @GetMapping("/")
    public String mainPage() {
        return "Calendar/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/join")
    public String join() {
        return "login/join";
    }
}

