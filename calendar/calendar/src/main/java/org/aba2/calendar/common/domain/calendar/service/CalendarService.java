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


    public List<CalendarEntity> getScheduleList(String userId) {
        return calendarRepository.findAllByUserId(userId);
    }

    public CalendarEntity register(CalendarEntity entity) {

        return calendarRepository.save(entity);

    }
}
