package org.aba2.calendar.common.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.friend.db.FriendRepository;
import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.errorcode.FriendErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;


    // 친구 목록 요청
    public List<FriendEntity> getFriendList(String userId) {
        return friendRepository.findFriendsByUserId(userId, FriendStatus.ACCEPTANCE);
    }

    // 친구 초대 요청
    public FriendEntity invitationRequest(String fromUserId, String toUserId) {

        var friendId = getFriendId(fromUserId, toUserId);

        var optionalEntity = friendRepository.findById(friendId);

        if (optionalEntity.isEmpty()) {
            var friendEntity = FriendEntity.builder()
                    .friendId(friendId)
                    .fromUserId(fromUserId)
                    .toUserId(toUserId)
                    .status(FriendStatus.WAIT)
                    .build()
                    ;

            return friendRepository.save(friendEntity);
        }

        var status = optionalEntity.get().getStatus();

        // TODO 상태에 따라 처리하기
        switch (status) {
            case WAIT :
                break;
            case BLOCK, ACCEPTANCE:
                throw new ApiException(ErrorCode.BAD_REQUEST);
            case DELETE, REJECTION:
                statusSettings(fromUserId, toUserId, FriendStatus.WAIT);
                break;
            default:
                throw new ApiException(ErrorCode.SERVER_ERROR);
        }

        return null;

    }

    // 초대 응답


    // 상태 변환
    public FriendEntity statusSettings(String fromUserId, String toUserId, FriendStatus status) {
        var friendId = getFriendId(fromUserId, toUserId);

        var friendEntity = friendRepository.findById(friendId)
                .orElseThrow(() -> new ApiException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendEntity.setStatus(status);

        return friendRepository.save(friendEntity);
    }


    public String getFriendId(String id1, String id2) {
        var arr = new String[]{id1, id2};
        Arrays.sort(arr);
        return String.join("_", arr);
    }


}
