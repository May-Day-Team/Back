package org.aba2.calendar.common.domain.groupuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUserInitialization {

    private String groupId;

    private String userId;

}
