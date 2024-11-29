package org.aba2.calendar.common.domain.calendar.db;

import org.aba2.calendar.common.domain.calendar.model.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    //user Id 조회 메서드
    List<CalendarEntity> findAllByUserId(String userId);

    //group ID 조회 메서드
    List<CalendarEntity> findAllByGroupId(String groupId);

}
