package org.aba2.calendar.common.domain.calendar.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.calendar.converter.CalendarConverter;
import org.aba2.calendar.common.domain.calendar.db.CalendarRepository;
import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.aba2.calendar.common.domain.calendar.model.CalendarGroupRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarRegisterRequest;
import org.aba2.calendar.common.domain.calendar.model.CalendarResponse;
import org.aba2.calendar.common.domain.calendar.service.CalendarService;
import org.aba2.calendar.common.domain.group.db.GroupRepository;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.groupuser.db.GroupUserRepository;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.CalendarErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

@Business
@RequiredArgsConstructor
public class CalendarBusiness {

    private final CalendarService calendarService;
    private final CalendarConverter calendarConverter;
    private final GroupUserRepository groupUserRepository;
    private final GroupRepository groupRepository;

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
        entity.setGroupCalendar(false); // 개인 일정인 경우

        var newEntity = calendarService.registerPersonalSchedule(entity);

        return calendarConverter.toResponse(newEntity);

    }

    //개인 스케줄 업데이트
    public CalendarResponse updatePersonalCalendar(Long calId, CalendarRegisterRequest req, User user) {
        CalendarEntity updateEntity = calendarConverter.toEntity(req, user);
        updateEntity.setCalId(calId); // 기존 일정 ID 설정
        CalendarEntity updatedEntity = calendarService.updatePersonSchedule(updateEntity);
        return calendarConverter.toResponse(updatedEntity);
    }

    // 개인 스케줄 삭제
    public void deletePersonalCalendar(Long calId, String userId) {
        calendarService.deletePersonalSchedule(calId, userId);
    }



    //그룹 스케줄 등록
    public CalendarResponse registerGroupCalendar(CalendarGroupRegisterRequest req, User user) {

        if (!isUserInGroup(req.getGroupId(), user.getId())) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        //CalendarGroupRegisterRequest -> CalendarEntity 변환
        var entity = calendarConverter.toGroupEntity(req, user);
        entity.setGroupCalendar(true); // 그룹 일정인 경우
        // 그룹 일정 저장
        var newEntity = calendarService.registerGroupSchedule(entity);

        // 저장된 데이터 응답으로 반환
        return calendarConverter.toResponse(newEntity);

    }

    // 그룹 스케줄 업데이트
    public CalendarResponse updateGroupCalendar(Long calId, CalendarGroupRegisterRequest req, String groupId, User user) {

        if (!isUserInGroup(groupId, user.getId())) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        CalendarEntity updateEntity = calendarConverter.toGroupEntity(req, user);
        updateEntity.setCalId(calId); // 기존 일정 ID 설정

        // GroupEntity를 GroupRepository로 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(CalendarErrorCode.GROUP_NOT_FOUND));

        // 그룹 정보 설정
        updateEntity.setGroup(group); // GroupEntity 설정

        CalendarEntity updatedEntity = calendarService.updateGroupSchedule(updateEntity);

        return calendarConverter.toResponse(updatedEntity);
    }




    //그룹 일정 삭제
    public void deleteGroupCalendar(Long calId, String groupId, User user) {

        if (!isUserInGroup(groupId, user.getId())) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        calendarService.deleteGroupSchedule(calId, groupId);
    }



    //그룹 스케줄 리스트
    public List<CalendarResponse> getScheduleGroupList(String groupId, String user) {

        if (!isUserInGroup(groupId, user)) {
            throw new ApiException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        // userId로 일정을 조회
        List<CalendarEntity> groupSchedules = calendarService.getGroupScheduleList(groupId);

        // CalendarEntity -> CalendarResponse 변환
        return groupSchedules.stream()
                .map(calendarConverter::toResponse)
                .toList()
                ;
    }


    //개인 스케줄 리스트
    public List<CalendarResponse> getScheduleList(String userId) {
        // userId로 일정을 조회
        List<CalendarEntity> entities = calendarService.getAllSchedules(userId);

        // CalendarEntity -> CalendarResponse 변환
        return entities.stream()
                .map(calendarConverter::toResponse)
                .toList()
                ;
    }

    //그룹 사용자 소속 확인 로직
    private boolean isUserInGroup (String groupId, String userId) {
        return groupUserRepository.existsByGroupIdAndUserId(groupId, userId);
    }


}
