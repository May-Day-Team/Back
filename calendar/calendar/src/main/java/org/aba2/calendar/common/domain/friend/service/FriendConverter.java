package org.aba2.calendar.common.domain.friend.service;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.FriendResponse;

@Converter
public class FriendConverter {

    public FriendEntity toEntity(FriendResponse response) {
        return FriendEntity.builder()
                .friendId(response.getFriendId())
                .fromUserId(response.getFromUserId())
                .toUserId(response.getToUserId())
                .status(response.getStatus())
                .build()
                ;
    }

    public FriendResponse toResponse(FriendEntity entity) {
        return FriendResponse.builder()
                .friendId(entity.getFriendId())
                .fromUserId(entity.getFromUserId())
                .toUserId(entity.getToUserId())
                .status(entity.getStatus())
                .build()
                ;
    }

}
