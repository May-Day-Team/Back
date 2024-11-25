package org.aba2.calendar.common.domain.groupuser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupRole;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "group_user_table")
@IdClass(GroupUserId.class)
public class GroupUserEntity {

    @Id
    private String groupId;

    @Id
    private String userId;

    @Enumerated(EnumType.STRING)
    private GroupRole role;

    @Enumerated(EnumType.STRING)
    private GroupStatus status;

}
