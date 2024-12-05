package org.aba2.calendar.common.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.calendar.converter.CalendarConverter;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.CalendarErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    // 개인 일정 조회
    public  List<CalendarEntity> getPersonalScheduleList(String userId) {
        return calendarRepository.findAllByUserIdAndIsGroupCalendarFalse(userId);
    }

    // 그룹 일정 조회
    public List<CalendarEntity> getGroupScheduleList(String groupId) {
        return calendarRepository.findAllByGroupIdAndIsGroupCalendarTrue(groupId);
    }

    //전체 일정 조회
    public List<CalendarEntity> getAllSchedules(String userId) {
        return calendarRepository.findAllByUserId(userId);
    }



    //새로운 개인 일정 등록
    public CalendarEntity registerPersonalSchedule(CalendarEntity entity) {
        entity.setGroupCalendar(false);
        return calendarRepository.save(entity);
    }

    // 새로운 그룹 일정 등록
    public CalendarEntity registerGroupSchedule(CalendarEntity entity) {
        entity.setGroupCalendar(true);
        return calendarRepository.save(entity);
    }


    // 개인 일정 수정
    public CalendarEntity updatePersonSchedule(CalendarEntity entity) {
        validateOwnership(entity.getCalId(), entity.getUserId(), false);
        return calendarRepository.save(entity);
    }

    // 그룹 일정 수정
    public CalendarEntity updateGroupSchedule(CalendarEntity entity) {
        validateOwnership(entity.getCalId(), entity.getGroupId(), true);
        return calendarRepository.save(entity);
    }


    //개인 일정 삭제
    public void deletePersonalSchedule(Long calId, String userId) {
        CalendarEntity entity = validateOwnership(calId, userId, false);
        calendarRepository.delete(entity);
    }


    //그룹 일정 삭제
    public void deleteGroupSchedule(Long calId, String groupId) {
        CalendarEntity entity = validateOwnership(calId, groupId, true);
        calendarRepository.delete(entity);
    }


    private  CalendarEntity validateOwnership(Long calId, String ownerId, boolean isGroup) {
        List<CalendarEntity> schedules = isGroup
                ? calendarRepository.findAllByGroupIdAndIsGroupCalendarTrue(ownerId)
                : calendarRepository.findAllByUserIdAndIsGroupCalendarFalse(ownerId);

        return schedules.stream()
                .filter(entity -> entity.getCalId().equals(calId))
                .findFirst()
                .orElseThrow(() -> new ApiException(CalendarErrorCode.CALENDAR_NOT_FOUND));
    }









}
