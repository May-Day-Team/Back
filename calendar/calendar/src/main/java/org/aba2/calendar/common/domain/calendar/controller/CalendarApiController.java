package org.aba2.calendar.common.domain.calendar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.calendar.business.CalendarBusiness;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.CalendarErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarApiController {

    private final CalendarBusiness calendarBusiness;

    //전체 스케줄 조회
    @GetMapping
    public Api<List<CalendarResponse>> getMainScheduleList(
            @UserSession User user
    ){
        var response = calendarBusiness.getMainScheduleList(user.getId());
        if (response == null || response.isEmpty()) {
            return Api.OK(Collections.emptyList());
        }

        return Api.OK(response);
    }

    //개인 스케줄 등록
    @PostMapping("/register")
    public ResponseEntity<Api<CalendarResponse>> register(
            @Valid
            @RequestBody CalendarRegisterRequest req,
            @UserSession User user
    ) {
        try {
            // 정상적으로 요청이 처리되었을 때
            CalendarResponse response = calendarBusiness.register(req, user);
            return ResponseEntity.ok(Api.OK(response));
        } catch (ApiException e) {
            e.printStackTrace();
            // ApiException 발생 시, 에러 코드와 설명을 포함하여 응답을 생성
            Api<?> errorResponse = Api.ERROR(e.getErrorCodeIfs());
            // ResponseEntity로 변환할 때, 제네릭 타입을 Api<CalendarResponse>로 변경
            return ResponseEntity.badRequest().body((Api<CalendarResponse>) errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            // 일반적인 예외 발생 시, 에러 코드와 설명을 포함하여 응답을 생성
            Api<?> errorResponse = Api.ERROR(CalendarErrorCode.UNKNOWN_ERROR);
            // ResponseEntity로 변환할 때, 제네릭 타입을 Api<CalendarResponse>로 변경
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((Api<CalendarResponse>) errorResponse);
        }
    }


    // 일정 수정
    @PutMapping("/{calId}")
    public ResponseEntity<Api<CalendarResponse>> update(
            @PathVariable Long calId,
            @RequestBody CalendarRegisterRequest req,
            @UserSession User user
    ) {
        CalendarEntity existingEntity = calendarBusiness.getCalendarEntityById(calId);

        CalendarResponse response = calendarBusiness.updatePersonalCalendar(calId, req, user);

        return ResponseEntity.ok(Api.OK(response));

    }

    // 일정 삭제
    @DeleteMapping("/{calId}")
    public ResponseEntity<Api<Void>> delete(
            @PathVariable Long calId,
            @UserSession User user
    ) {
        // 개인 일정만 삭제할 수 있도록 확인
        CalendarEntity existingEntity = calendarBusiness.getCalendarEntityById(calId);
        if (!existingEntity.getUserId().equals(user.getId())) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 개인 일정 삭제
        calendarBusiness.deletePersonalCalendar(calId, user.getId());

        return ResponseEntity.ok(Api.OK(null));
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
        log.info("Request: {}", req);
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
