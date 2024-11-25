package org.aba2.calendar.common.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.group.model.GroupCreateRequest;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.group.model.GroupResponse;
import org.aba2.calendar.common.domain.groupuser.business.GroupUserBusiness;

@Converter
@RequiredArgsConstructor
public class GroupConverter {

    private final GroupUserBusiness groupUserBusiness;

    public GroupResponse toResponse(GroupEntity entity) {
        return GroupResponse.builder()
                .groupId(entity.getGroupId())
                .groupName(entity.getGroupName())
                .profileUrl(entity.getProfileUrl())
                .groupMembers(groupUserBusiness.findAllByGroupId(entity.getGroupId()))
                .build();
    }

}
