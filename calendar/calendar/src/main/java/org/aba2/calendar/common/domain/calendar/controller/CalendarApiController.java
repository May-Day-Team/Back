package org.aba2.calendar.common.domain.calendar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.calendar.business.CalendarBusiness;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarApiController {

    private final CalendarBusiness calendarBusiness;

    //전체 스케줄 조회
    @GetMapping
    public Api<Map<String, List<Map<String, String>>>> getMainScheduleListForFrontend(
            @UserSession User user
    ){
        log.debug("Fetching main schedule list for user: {}", user.getId());

        return Api.OK(calendarBusiness.getMainScheduleListForFrontend(user.getId()));
    }

    //개인 캘린더 조회
    @GetMapping("/person-schedule")
    public Api<List<CalendarResponse>> getScheduleList(
            @UserSession
            User user
    ) {
        log.debug("Fetching personal schedule list for user: {}", user.getId());
        return Api.OK(calendarBusiness.getScheduleList(user.getId()));
    }

    //그룹 캘린더 조회
    @GetMapping("/group-schedule")
    public Api<List<CalendarResponse>> getScheduleGroupList(
            @RequestParam("groupId") String groupId,
            @UserSession User user
    ) {
        log.debug("Fetching group schedule list for user: {}, group: {}", user.getId(), groupId);
        return Api.OK(calendarBusiness.getScheduleGroupList(groupId, user.getId()));
    }


    //개인 스케줄 등록
    @PostMapping("/register")
    public ResponseEntity<Api<CalendarResponse>> registerPersonalSchedule(
            @Valid
            @RequestBody CalendarRegisterRequest req,
            @UserSession User user
    ) {
        log.info("Registering personal schedule for user: {}", user.getId());
        Api<CalendarResponse> response = Api.OK(calendarBusiness.register(req, user));
        return ResponseEntity.ok(response);

    }


    //그룹 스케줄 등록
    @PostMapping("/group_register")
    public Api<CalendarResponse> registerGroupCalendar(
            @Valid
            @RequestBody CalendarGroupRegisterRequest req,
            @UserSession User user
    ){
        log.info("Registering group schedule for group: {}", req.getGroupId());

        CalendarResponse response = calendarBusiness.registerGroupCalendar(req, user);

        return Api.OK(response);
    }


    // 개인 캘린더 일정 수정
    @PutMapping("/{calId}")
    public ResponseEntity<Api<CalendarResponse>> updateSchedule(
            @PathVariable Long calId,
            @Valid
            @RequestBody CalendarRegisterRequest req,
            @UserSession User user
    ) {
        log.info("Updating schedule (ID: {}) for user: {}", calId, user.getId());

        Api<CalendarResponse> response = Api.OK(calendarBusiness.updatePersonalCalendar(calId, req, user));
        return ResponseEntity.ok(response);

    }

    // 그룹 캘린더 일정 수정
    @PutMapping("/group/{calId}")
    public ResponseEntity<Api<CalendarResponse>> updateGroupSchedule(
            @PathVariable Long calId,
            @Valid
            @RequestBody CalendarGroupRegisterRequest req,
            @UserSession User user
    ) {
        log.info("Updating group schedule (ID: {}) for group: {}", calId, req.getGroupId());

        CalendarResponse response = calendarBusiness.updateGroupCalendar(calId, req, req.getGroupId(), user);

        Api<CalendarResponse> apiResponse = Api.OK(response);

        return ResponseEntity.ok(apiResponse);

    }


    // 개인 캘린더 일정 삭제
    @DeleteMapping("/{calId}")
    public ResponseEntity<Api<Void>> deletePersonalSchedule(
            @PathVariable Long calId,
            @UserSession User user
    ) {
        log.info("Deleting schedule (ID: {}) for user: {}", calId, user.getId());
        calendarBusiness.deletePersonalCalendar(calId, user.getId());
        return ResponseEntity.ok(null);
    }


    // 그룹 캘린더 일정 삭제
    @DeleteMapping("group/{calId}")
    public ResponseEntity<Api<Void>> deleteGroupSchedule(
            @PathVariable Long calId,
            @UserSession User user,
            @RequestBody CalendarGroupRegisterRequest req
    ) {
        log.info("Deleting Group schedule (ID: {}) for user: {} req : {}", calId, user.getId(), req);

        calendarBusiness.deleteGroupCalendar(calId, req.getGroupId(), user);
        return ResponseEntity.ok(null);
    }



}
