package org.aba2.calendar.common.domain.calendar.converter;

import jakarta.persistence.EntityNotFoundException;
import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;
import org.aba2.calendar.common.domain.group.db.GroupRepository;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter
public class CalendarConverter {


    private final GroupRepository groupRepository;

    @Autowired
    public CalendarConverter(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public CalendarEntity toEntity(CalendarRegisterRequest req, User user) {

        var startDate = combineDateAndTime(req.getStartDate(), LocalTime.MIDNIGHT);
        var endDate = combineDateAndTime(req.getEndDate(), LocalTime.MIDNIGHT);

        var startTime = combineDateAndTime(req.getStartDate(), convertTime(req.getStartTime()));
        var endTime = combineDateAndTime(req.getEndDate(), convertTime(req.getEndTime()));
        var ringAt = req.getRingAt() != null ? combineDateAndTime(req.getStartDate(), convertTime(req.getRingAt())) : null;
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

        GroupEntity group = groupRepository.findById(req.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없습니다."));


        return CalendarEntity.builder()
                .group(group)
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .startDate(startDate.toLocalDate())
                .endTime(endDate.toLocalTime())
                .startTime(convertTime(req.getStartTime()))
                .endTime(convertTime(req.getEndTime()))
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
                .groupId(entity.getGroup() != null ? entity.getGroup().getGroupId() : null)
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





    public LocalDateTime combineDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public LocalTime convertTime(String time) {
        return LocalTime.parse(time);
    }


}
