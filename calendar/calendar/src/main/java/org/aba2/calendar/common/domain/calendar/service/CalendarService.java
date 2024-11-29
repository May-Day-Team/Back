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
        return calendarRepository.findAllByUserId(userId);
    }

    //개인 일정 ID
    public CalendarEntity getPersonalEntityById(Long calId, String userId) {
        return calendarRepository.findAllByUserId(userId).stream()
                .filter(entity -> entity.getCalId().equals((calId)))
                .findFirst()
                .orElseThrow(() -> new ApiException(CalendarErrorCode.CALENDAR_NOT_FOUND));
    }

    //그룹 일정 ID
    public CalendarEntity getGroupEntityById(Long calId, String groupId) {
        return calendarRepository.findAllByUserId(groupId).stream()
                .filter(entity -> entity.getCalId().equals((calId)))
                .findFirst()
                .orElseThrow(() -> new ApiException(CalendarErrorCode.CALENDAR_NOT_FOUND));
    }

    // 그룹 일정 조회
    public List<CalendarEntity> getGroupScheduleList(String groupId) {
        return calendarRepository.findAllByGroupId(groupId);
    }

    //전체 일정 조회
    public List<CalendarEntity> getScheduleList(String userId) {
        return calendarRepository.findAllByUserId(userId);
    }


    //새로운 개인 일정 등록
    public CalendarEntity register(CalendarEntity entity) {
        return calendarRepository.save(entity);
    }

    //새로운 그룹 일정 등록
    public CalendarEntity registerGroupCalendar(CalendarEntity entity) {
        return calendarRepository.save(entity);
    }

    //개인 일정 업데이트
    public CalendarEntity updatePersonalCalendar(CalendarEntity entity) {
        return calendarRepository.save(entity);
    }

    // 개인 일정 삭제
    public void deletePersonalCalendar(Long calId, String userId) {
        CalendarEntity entity = getPersonalEntityById(calId, userId);

        if (!entity.getUserId().equals(userId)) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        calendarRepository.delete(entity);
    }

    // 그룹 일정 삭제
    public void deleteGroupCalendar(Long calId, String groupId) {
        CalendarEntity entity = getGroupEntityById(calId, groupId);
        calendarRepository.delete(entity);
    }




}
