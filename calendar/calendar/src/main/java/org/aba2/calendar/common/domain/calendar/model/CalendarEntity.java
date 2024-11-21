package org.aba2.calendar.common.domain.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;

import java.time.LocalDateTime;

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
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    // 제목
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    // 내용
    @Column(name = "content", nullable = false, length = 255)
    private String content;

    // 날짜
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    // 시작 시간
    @Column(name = "start_time")
    private LocalDateTime startTime;

    // 종료 시간
    @Column(name = "end_time")
    private LocalDateTime endTime;

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

    // 반복 여부
    @Column(name = "repeat_yn", length = 1)
    private String repeatYn;

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

}
