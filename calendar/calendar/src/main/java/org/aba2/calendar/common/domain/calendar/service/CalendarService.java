package org.aba2.calendar.common.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
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

    // 그룹 일정 조회
    public List<CalendarEntity> getGroupScheduleList(String groupId) {
        return calendarRepository.findAllGroupId(groupId);
    }

    //전체 일정 조회
    public List<CalendarEntity> getScheduleList(String userId) {
        return calendarRepository.findAllByUserId(userId);
    }

    //새로운 일정 등록
    public CalendarEntity register(CalendarEntity entity) {

        return calendarRepository.save(entity);

    }
}
