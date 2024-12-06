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
    @Column(name = "cal_id")
    private Long calId;

    // user_id
    @Column(name = "user_id", nullable = false, length = 30)
    private String userId;

    //group_id
//    @Column(name = "group_id", nullable = false, length = 30)
//    private String groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    // 그룹 일정 여부
    @Column(name = "is_group_calendar", nullable = false)
    private boolean isGroupCalendar;

    // 제목
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    // 내용
    @Column(name = "content", nullable = false, length = 255)
    private String content;

    // 날짜
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // 시작 시간
    @Column(name = "start_time")
    private LocalTime startTime;

    // 종료 시간
    @Column(name = "end_time")
    private LocalTime endTime;

    // 기념일 여부
    @Column(name = "memorial_yn", length = 1)
    private String memorialYn;

    // 차단 여부
    @Column(name = "block_yn", length = 1)
    private String blockYn;

    // 메모
    @Column(name = "memo", length = 255)
    private String memo;

    // 장소
    @Column(name = "place", length = 255)
    private String place;

    // 이벤트 여부
    @Column(name = "event_yn", length = 1)
    private String eventYn;

    //반복되는 요일 mon, tue 이렇게 들어옴 여러 날짜가 들어올 수도 있음
    @Column(name = "repeat_day", length = 50)
    private String repeatDay;

    // 태그 코드
    @Column(name = "tag_code", length = 50)
    private String tagCode;

    // 알림 시간
    @Column(name = "ring_at")
    private LocalDateTime ringAt;

    // 색상
    @Enumerated(EnumType.STRING)
    @Column(name = "color", length = 40, nullable = false, columnDefinition = "VARCHAR(40) DEFAULT 'BLACK'")
    private Colors color;


    // CalendarEntity 클래스
    public void update(CalendarEntity updatedEntity) {
        this.title = updatedEntity.getTitle();
        this.content = updatedEntity.getContent();
        this.startDate = updatedEntity.getStartDate();
        this.endDate = updatedEntity.getEndDate();
        this.startTime = updatedEntity.getStartTime();
        this.endTime = updatedEntity.getEndTime();
        this.memorialYn = updatedEntity.getMemorialYn();
        this.place = updatedEntity.getPlace();
        this.color = updatedEntity.getColor();
        this.ringAt = updatedEntity.getRingAt();
        this.repeatDay = updatedEntity.getRepeatDay();
        this.tagCode = updatedEntity.getTagCode();
        this.blockYn = updatedEntity.getBlockYn();
        this.eventYn = updatedEntity.getEventYn();
        this.memo = updatedEntity.getMemo();
    }



}
