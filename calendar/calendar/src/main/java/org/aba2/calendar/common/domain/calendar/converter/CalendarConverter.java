package org.aba2.calendar.common.domain.calendar.converter;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;
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

        var startDate = combineDateAndTime(req.getStartDate(), LocalTime.MIDNIGHT);
        var endDate = combineDateAndTime(req.getEndDate(), LocalTime.MIDNIGHT);

        var startTime = combineDateAndTime(req.getStartDate(), req.getStartTime());
        var endTime = combineDateAndTime(req.getEndDate(), req.getEndTime());
        var ringAt = req.getRingAt() != null ? combineDateAndTime(req.getStartDate(), req.getRingAt().toLocalTime()) : null;
        Colors color = req.getColor() != null ? Colors.valueOf(req.getColor().toString()) : Colors.WHITE;


        return CalendarEntity.builder()
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .startDate(startDate.toLocalDate())
                .startTime(startTime.toLocalTime())
                .endDate(endDate.toLocalDate())
                .endTime(endTime.toLocalTime())
                .memorialYn("N")
                .blockYn("N")
                .memo("Null")
                .place(req.getPlace())
                .eventYn("N")
                .repeatDay("None")
                .tagCode("No Tage")
                .ringAt(ringAt)
                .color(color)
                .build()
                ;
    }

    public CalendarEntity toGroupEntity(CalendarGroupRegisterRequest req, User user) {

        var startDate = combineDateAndTime(req.getStartDate(), LocalTime.MIDNIGHT);
        var endDate = combineDateAndTime(req.getEndDate(), LocalTime.MIDNIGHT);
        Colors color = req.getColor() != null ? Colors.valueOf(req.getColor().toString()) : Colors.WHITE;


        return CalendarEntity.builder()
                .groupId(req.getGroupId())
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .startDate(startDate.toLocalDate())
                .endTime(endDate.toLocalTime())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .memorialYn("N")
                .blockYn("N")
                .memo("Null")
                .place(req.getPlace())
                .eventYn("N")
                .repeatDay("None")
                .tagCode("No Tage")
                .ringAt(null)
                .color(color)
                .build();


    }



    public CalendarResponse toResponse(CalendarEntity entity) {
        return CalendarResponse.builder()
                .groupId(entity.getGroupId() != null ? entity.getGroupId() : null)
                .calId(entity.getCalId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .startDate(entity.getStartDate())
                .startTime(entity.getStartTime())
                .endDate(entity.getEndDate())
                .endTime(entity.getEndTime())
                .ringAt(entity.getRingAt())
                .place(entity.getPlace())
                .repeatDay(entity.getRepeatDay())
                .color(entity.getColor())
                .build()
                ;
    }

//    public LocalDateTime combineDateAndTime(String date, String time) {
//
//        var localDate = LocalDate.parse(date, dateFormatter);
//        var localTime = LocalTime.parse(time, timeFormatter);
//
//        return LocalDateTime.of(localDate, localTime);
//    }

    public LocalDateTime combineDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }


}
