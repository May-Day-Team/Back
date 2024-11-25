package org.aba2.calendar.common.domain.groupuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUserId {

    private String groupId;

    private String userId;

}
