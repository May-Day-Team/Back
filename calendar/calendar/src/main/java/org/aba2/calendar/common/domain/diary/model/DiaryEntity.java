package org.aba2.calendar.common.domain.diary.model;

import jakarta.persistence.*;
import lombok.*;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@DynamicUpdate
@Table(name = "diary")
public class DiaryEntity {

    @EmbeddedId
    private DiaryId diaryId;

    private String title;

    private String content;

    private String tag;

    private String weather;

    private LocalDate createAt;

    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        this.diaryId = new DiaryId(createAt, user.getUserId());
    }

    public void updateDiary(String title, String content, String tag, String weather) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.weather = weather;
        this.updateAt = LocalDate.now();
    }
}
