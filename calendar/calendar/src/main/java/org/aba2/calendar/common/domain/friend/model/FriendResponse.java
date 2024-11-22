package org.aba2.calendar.common.domain.friend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendResponse {

    private String friendId;

    private String fromUserId; // 친구 요청을 보낸 사용자 ID

    private String toUserId;   // 친구 요청을 받은 사용자 ID

    private FriendStatus status;

}
