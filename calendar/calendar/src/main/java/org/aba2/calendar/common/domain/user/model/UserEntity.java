package org.aba2.calendar.common.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.diary.model.DiaryEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;

    private String password;

    private String name;

    private LocalDateTime birthdate;

    private String email;

    private String phoneNumber;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<DiaryEntity> diaries;

}
