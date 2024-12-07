package org.aba2.calendar.common.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.friend.db.FriendRepository;
import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.FriendResponse;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.aba2.calendar.common.domain.user.db.UserRepository;
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
    private final UserRepository userRepository;


    // 친구 목록 요청
    public List<FriendEntity> getFriendList(String userId) {
        return friendRepository.findFriendsByUserId(userId, FriendStatus.ACCEPTANCE);
    }

    // 친구 초대 요청
    public FriendEntity invitationRequest(String fromUserId, String toUser) {
        // 전화번호 혹은 유저 아이디로 친구 추가
        String toUserId = getUserIdByRequestWithThrow(toUser);

        var friendId = getFriendId(fromUserId, toUserId);

        // 요청을 보냈을 때 이미 요청이 있었다면 처리하기
        var optionalFriendEntity = friendRepository.findById(friendId);
        if (optionalFriendEntity.isPresent()) {
            var friendEntity = optionalFriendEntity.get();
            var status = friendEntity.getStatus();

            switch (status) {
                // 승락, 차단 일 때 요청 무시하기
                case ACCEPTANCE, BLOCK:
                    return friendEntity;
                // 거절, 삭제 한 상태일 때
                case REJECTION, DELETE:
                    friendEntity.setStatus(FriendStatus.WAIT);
                    return friendRepository.save(friendEntity);
                case WAIT:
                    // 요청한 사람이 또 요청을 보냈을 때 (잘못된 요청)
                    if (friendEntity.getFromUserId().equals(fromUserId)) {
                        throw new ApiException(ErrorCode.BAD_REQUEST, "이미 친구 요청을 보냈습니다.");
                    }

                    // 요청 받은 사람도 요청을 보냈을 때 (서로 보냈으니 승락하기)
                    else {
                        friendEntity.setStatus(FriendStatus.ACCEPTANCE);
                        return friendRepository.save(friendEntity);
                    }
            }
        }

        // 요청이 없었다면 새로 생성해서 저장하기
        var newEntity = FriendEntity.builder()
                .friendId(friendId)
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .status(FriendStatus.WAIT)
                .build()
                ;

        return friendRepository.save(newEntity);
    }

    private String getUserIdByRequestWithThrow(String toUser) {
        return userRepository.findByUserIdOrPhoneNumber(toUser, toUser)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST))
                .getUserId();
    }

    // 초대 응답
    public FriendEntity invitationResponse(String fromUserId, String toUserId) {

        // 초대를 보낸 상태인지 확인하고 초대를 받은 사람이 수락한 것인지 확인하기
        var friendId = getFriendId(fromUserId, toUserId);

        var friendEntity = findByFriendIdWithThrow(friendId);

        // 대기 상태가 아닐 때
        if (!friendEntity.getStatus().equals(FriendStatus.WAIT)) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        // 대기 상태이지만 받은 사람이 초대 응답을 받았는지
        if (!friendEntity.getFromUserId().equals(toUserId)) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        // 해당 요청이 정상적일 때 친구 등록
        friendEntity.setStatus(FriendStatus.ACCEPTANCE);

        return friendRepository.save(friendEntity);

    }


    // 상태 변환 (친구 삭제, 요청, 대기, 수락, 차단)
    public FriendEntity statusSettings(String fromUserId, String toUserId, FriendStatus status) {
        var friendId = getFriendId(fromUserId, toUserId);

        var friendEntity = findByFriendIdWithThrow(friendId);

        friendEntity.setStatus(status);

        return friendRepository.save(friendEntity);
    }


    public String getFriendId(String id1, String id2) {
        var arr = new String[]{id1, id2};
        Arrays.sort(arr);
        return String.join("_", arr);
    }

    public FriendEntity findByFriendIdWithThrow(String friendId) {
        return friendRepository.findById(friendId)
                .orElseThrow(() -> new ApiException(FriendErrorCode.FRIEND_NOT_FOUND));
    }

    // 자신에게 요청 온 친구 리스트 출력
    public List<FriendEntity> findAllByInvitationRequest(String userId) {
        return friendRepository.findAllByToUserIdAndStatus(userId, FriendStatus.WAIT);
    }

    public List<FriendEntity> getBlockList(String id) {

        return friendRepository.findAllByToUserIdAndStatus(id, FriendStatus.BLOCK);

    }
}
