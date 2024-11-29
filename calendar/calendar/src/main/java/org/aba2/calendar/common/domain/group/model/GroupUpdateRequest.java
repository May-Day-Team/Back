package org.aba2.calendar.common.domain.group.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUpdateRequest {

    private String groupId;

    private String groupName;

    private String profileUrl;

}
