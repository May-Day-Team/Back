package org.aba2.calendar.common.domain.record.model;

import jakarta.persistence.*;
import lombok.*;
import org.aba2.calendar.common.domain.record.model.enums.Weather;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@DynamicUpdate
@Table(name = "record")
public class RecordEntity {

    @EmbeddedId
    private RecordId recordId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Weather weather;

    private LocalDate updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // recordId의 userId를 UserEntity와 매핑
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void updateRecord(String title, String content, Weather weather) {
        this.title = title;
        this.content = content;
        this.weather = weather;
        this.updateAt = LocalDate.now();
    }
}