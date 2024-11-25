package org.aba2.calendar.common.domain.groupuser.service;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserEntity;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserResponse;

@Converter
public class GroupUserConverter {

    public GroupUserResponse toResponse(GroupUserEntity entity) {
        return GroupUserResponse.builder()
                .groupId(entity.getGroupId())
                .userId(entity.getUserId())
                .role(entity.getRole().toString())
                .build();
    }

}
