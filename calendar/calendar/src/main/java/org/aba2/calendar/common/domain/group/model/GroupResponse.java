package org.aba2.calendar.common.domain.group.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {

    private String groupId;

    private String groupName;

    private String profileUrl;

    private List<GroupUserResponse> groupMembers;

}
