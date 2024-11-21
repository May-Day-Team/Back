package org.aba2.calendar.common.domain.calendar.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.calendar.converter.CalendarConverter;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.calendar.service.CalendarService;
import org.aba2.calendar.common.domain.user.model.User;

import java.util.List;

@Business
@RequiredArgsConstructor
public class CalendarBusiness {

    private final CalendarService calendarService;
    private final CalendarConverter calendarConverter;


    public CalendarResponse register(CalendarRegisterRequest req, User user) {

        var entity = calendarConverter.toEntity(req, user);

        var newEntity = calendarService.register(entity);

        return calendarConverter.toResponse(newEntity);

    }

    public List<CalendarResponse> getScheduleList(String userId) {
        // userId로 일정을 조회
        List<CalendarEntity> entities = calendarService.getScheduleList(userId);

        // CalendarEntity -> CalendarResponse 변환
        return entities.stream()
                .map(calendarConverter::toResponse)
                .toList()
                ;
    }


}
