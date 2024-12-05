package org.aba2.calendar.common.domain.group.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
@Table(name = "group")
public class GroupEntity {

    @Id
    private String groupId;

    private String groupName;

    private String profileUrl;

    private LocalDateTime createAt;

}
