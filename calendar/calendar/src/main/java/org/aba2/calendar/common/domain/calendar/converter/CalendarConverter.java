package org.aba2.calendar.common.domain.calendar.converter;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter
public class CalendarConverter {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public CalendarEntity toEntity(CalendarRegisterRequest req, User user) {

        var startDate = combineDateAndTime(req.getStartDate(), "00:00");
        var endDate = combineDateAndTime(req.getEndDate(), "00:00");

        var startTime = combineDateAndTime(req.getStartDate(), req.getStartTime());
        var endTime = combineDateAndTime(req.getEndDate(), req.getEndTime());
        var ringAt = combineDateAndTime(req.getStartDate(), req.getRingAt());

        return CalendarEntity.builder()
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .startDate(startDate)
                .startTime(startTime)
                .endDate(endDate)
                .endTime(endTime)
                .memorialYn("N")
                .blockYn("N")
                .memo("Null")
                .place(req.getPlace())
                .eventYn("N")
                .repeatYn("N")
                .tagCode("No Tage")
                .ringAt(ringAt)
                .color(req.getColor())
                .build()
                ;
    }


    public CalendarResponse toResponse(CalendarEntity entity) {
        return CalendarResponse.builder()
                .calId(entity.getCalId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .startDate(entity.getStartDate())
                .startTime(entity.getStartTime())
                .endDate(entity.getEndDate())
                .endTime(entity.getEndTime())
                .ringAt(entity.getRingAt())
                .blockYn(entity.getBlockYn())
                .place(entity.getPlace())
                .repeatYn(entity.getRepeatYn())
                .color(entity.getColor())
                .build()
                ;
    }

    public LocalDateTime combineDateAndTime(String date, String time) {

        var localDate = LocalDate.parse(date, dateFormatter);
        var localTime = LocalTime.parse(time, timeFormatter);

        return LocalDateTime.of(localDate, localTime);
    }

}
