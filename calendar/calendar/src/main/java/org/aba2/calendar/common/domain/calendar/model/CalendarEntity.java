package org.aba2.calendar.common.domain.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;
import org.aba2.calendar.common.domain.group.model.GroupEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "calendar")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEntity {

    // 자동 증가되는 cal_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calId;

    // user_id
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    // 그룹 일정 여부
    private boolean isGroupCalendar;

    // 제목
    private String title;

    // 내용
    private String content;

    // 날짜
    private LocalDate startDate;

    private LocalDate endDate;

    // 시작 시간
    private LocalTime startTime;

    // 종료 시간
    private LocalTime endTime;

    // 기념일 여부
    private String memorialYn;

    // 차단 여부
    private String blockYn;

    // 메모
    private String memo;

    // 장소
    private String place;

    // 이벤트 여부
    private String eventYn;

    //반복되는 요일 mon, tue 이렇게 들어옴 여러 날짜가 들어올 수도 있음
    private String repeatDay;

    // 태그 코드
    private String tagCode;

    // 알림 시간
    private LocalDateTime ringAt;

    // 색상
    @Enumerated(EnumType.STRING)
    private Colors color;
}
