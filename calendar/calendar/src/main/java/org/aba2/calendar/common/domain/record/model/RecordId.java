package org.aba2.calendar.common.domain.record.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecordId implements Serializable {

    private LocalDate createAt;

    private String userId;

    public Object getDate() {
        return createAt;
    }
}
