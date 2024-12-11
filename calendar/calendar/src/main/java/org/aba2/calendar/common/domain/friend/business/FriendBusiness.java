package org.aba2.calendar.common.domain.friend.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.friend.model.FriendResponse;
import org.aba2.calendar.common.domain.friend.model.enums.FriendInvitation;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.aba2.calendar.common.domain.friend.service.FriendConverter;
import org.aba2.calendar.common.domain.friend.service.FriendService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.List;
import java.util.Objects;

@Business
@RequiredArgsConstructor
public class FriendBusiness {

    private final FriendService friendService;

    private final FriendConverter friendConverter;


    // TODO 전화번호로 친구 추가 만들기
    // TODO 중복된 코드 처리하기
    // 친구 추가 요청
    public FriendResponse invitationRequest(User user, FriendInvitation req) {
        if (Objects.isNull(user) || user.getId() == null || req.getToUserId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var fromUserId = user.getId();

        var response = friendService.invitationRequest(fromUserId, req.getToUserId());

        return friendConverter.toResponse(response);
    }

    // TODO 중복된 코드 처리하기
    // 친구 추가 응답
    public FriendResponse invitationResponse(User user, FriendInvitation req) {
        if (Objects.isNull(user) || user.getId() == null || req.getToUserId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var fromUserId = user.getId();

        var response = friendService.invitationResponse(fromUserId, req.getToUserId());

        return friendConverter.toResponse(response);
    }


    // 친구 상태 변환(삭제, 차단)
    public FriendResponse statusSettings(User user, String toUserId, FriendStatus status) {
        if (Objects.isNull(user) || user.getId() == null || toUserId == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var fromUserId = user.getId();

        var response = friendService.statusSettings(fromUserId, toUserId, status);

        return friendConverter.toResponse(response);
    }


    // 친구 목록 보기
    public List<FriendResponse> getFriendList(User user) {
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var userId = user.getId();

        return friendService.getFriendList(userId).stream()
                .map(friendConverter::toResponse)
                .toList();
    }


    // 자신에게 친구 요청온 리스트 출력
    public List<FriendResponse> findByInvitationRequest(User user) {
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var userId = user.getId();

        return friendService.findAllByInvitationRequest(userId).stream()
                .map(friendConverter::toResponse)
                .toList()
                ;
    }

    public List<FriendResponse> getBlockList(User user) {
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        return friendService.getBlockList(user.getId()).stream()
                .map(friendConverter::toResponse)
                .toList();
    }
}
