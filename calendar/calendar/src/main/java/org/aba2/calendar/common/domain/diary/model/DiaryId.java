package org.aba2.calendar.common.domain.diary.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DiaryId implements Serializable {

    private LocalDate createAt;

    private String userId;
}
