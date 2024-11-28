package org.aba2.calendar.common.domain.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.calendar.business.CalendarBusiness;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarApiController {

    private final CalendarBusiness calendarBusiness;

    //전체 스케줄 조회
    @GetMapping
    public Api<List<CalendarResponse>> getMainScheduleList(
            @UserSession User user
    ){
        var response = calendarBusiness.getMainScheduleList(user.getId());
        return Api.OK(response);
    }

    //개인 스케줄 등록
    @PostMapping("/register")
    public Api<CalendarResponse> register(
            @RequestBody
            CalendarRegisterRequest req,
            @UserSession User user
    ) {
        System.out.println(req);
        return Api.OK(calendarBusiness.register(req, user));
    }


    //개인 스케줄 리스트
    @GetMapping("/schedule-list")
    public Api<List<CalendarResponse>> getScheduleList(
            @UserSession
            User user
    ) {
        var response = calendarBusiness.getScheduleList(user.getId());
        return Api.OK(response);
    }


    //그룹 스케줄 등록
    @PostMapping("/group_register")
    public Api<CalendarResponse> registerGroupCalendar(
            @RequestBody CalendarGroupRegisterRequest req,
            @UserSession User user
            ){
        System.out.println(req);
        return Api.OK(calendarBusiness.registerGroupCalendar(req,user));
    }


    //그룹 스케줄 리스트
    @GetMapping("/schedule-grouplist")
    public Api<List<CalendarResponse>> getScheduleGroupList(
            @UserSession
            User user
    ) {
        var response = calendarBusiness.getScheduleGroupList(user.getId());
        return Api.OK(response);
    }

}
