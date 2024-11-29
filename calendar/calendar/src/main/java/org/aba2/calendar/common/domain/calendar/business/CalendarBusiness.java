package org.aba2.calendar.common.domain.calendar.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.calendar.converter.CalendarConverter;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.calendar.service.CalendarService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.CalendarErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.List;
import java.util.stream.Stream;

@Business
@RequiredArgsConstructor
public class CalendarBusiness {

    private final CalendarService calendarService;
    private final CalendarConverter calendarConverter;

    private final CalendarRepository calendarRepository;

    public CalendarEntity getCalendarEntityById(Long calId) {
        return calendarRepository.findById(calId)
                .orElseThrow(() -> new ApiException(CalendarErrorCode.CALENDAR_NOT_FOUND));
    }

    // 메인 스케줄 조회 (개인 + 그룹 일정 합치기)
    public List<CalendarResponse> getMainScheduleList(String userId) {

        // 개인 일정 조회
        var personalSchedules = calendarService.getPersonalScheduleList(userId);

        // 그룹 일정 조회
        var groupSchedules = calendarService.getGroupScheduleList(userId);

        // 두 일정을 합쳐 반환
        var allSchedules = Stream.concat(personalSchedules.stream(), groupSchedules.stream())
                .map(calendarConverter::toResponse)
                .toList();
        return allSchedules;
    }



    //개인 스케줄 등록
    public CalendarResponse register(CalendarRegisterRequest req, User user) {

        // 검증 로직 추가
        if (req.getStartDate() == null || req.getEndDate() == null) {
            throw new ApiException(CalendarErrorCode.MISSING_REQUIRED_FIELDS);
        }

        var entity = calendarConverter.toEntity(req, user);

        var newEntity = calendarService.register(entity);

        return calendarConverter.toResponse(newEntity);

    }

    //개인 스케줄 업데이트
    public CalendarResponse updatePersonalCalendar(Long calId, CalendarRegisterRequest req, User user) {
        CalendarEntity updateEntity = calendarConverter.toEntity(req, user);
        updateEntity.setCalId(calId); // 기존 일정 ID 설정
        CalendarEntity updatedEntity = calendarService.updatePersonalCalendar(updateEntity);
        return calendarConverter.toResponse(updatedEntity);
    }

    // 개인 일정 삭제
    public void deletePersonalCalendar(Long calId, String userId) {
        calendarService.deletePersonalCalendar(calId, userId);
    }



    //그룹 스케줄 등록
    public CalendarResponse registerGroupCalendar(CalendarGroupRegisterRequest req, User user) {

        //CalendarGroupRegisterRequest -> CalendarEntity 변환
        var entity = calendarConverter.toGroupEntity(req, user);

        // 그룹 일정 저장
        var newEntity = calendarService.register(entity);

        // 저장된 데이터 응답으로 반환
        return calendarConverter.toResponse(newEntity);

    }

    // 그룹 일정 업데이트
//    public CalendarResponse updateGroupCalendar(Long calId, CalendarRegisterRequest req, String groupId, User user) {
//        CalendarEntity updateEntity = calendarConverter.toEntity(req, user);
//        updateEntity.setCalId(calId); // 기존 일정 ID 설정
//        updateEntity.setGroupId(groupId); // 그룹 ID 설정
//        CalendarEntity updatedEntity = calendarService.updateGroupCalendar(updateEntity);
//        return calendarConverter.toResponse(updatedEntity);
//    }


    //그룹 일정 삭제
    public void deleteGroupCalendar(Long calId, String groupId) {
        calendarService.deleteGroupCalendar(calId, groupId);
    }



    //그룹 스케줄 리스트
    public List<CalendarResponse> getScheduleGroupList(String groupId) {
        // userId로 일정을 조회
        List<CalendarEntity> entities = calendarService.getGroupScheduleList(groupId);

        // CalendarEntity -> CalendarResponse 변환
        return entities.stream()
                .map(calendarConverter::toResponse)
                .toList()
                ;
    }


    //개인 스케줄 리스트
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
