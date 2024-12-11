package org.aba2.calendar.common.domain.calendar.db;

import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    // 개인 캘린더 일정만 조회
    List<CalendarEntity> findAllByUserIdAndIsGroupCalendarFalse(String userId);

    // 그룹 캘린더 일정만 조회 (수정: group.groupId를 사용하도록 변경)
    List<CalendarEntity> findAllByGroup_GroupIdAndIsGroupCalendarTrue(String groupId);

    // 개인 캘린더 + 그룹 캘린더 조회
    List<CalendarEntity> findAllByUserId(String userId);

}
