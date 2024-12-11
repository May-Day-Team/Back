package org.aba2.calendar.common.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.friend.business.FriendBusiness;
import org.aba2.calendar.common.domain.friend.model.FriendResponse;
import org.aba2.calendar.common.domain.friend.model.enums.FriendInvitation;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendApiController {

    private final FriendBusiness friendBusiness;

    // 친구 초대 요청
    @PostMapping("/invitation/request")
    public Api<FriendResponse> invitationRequest(
            @UserSession User user,
            @RequestBody FriendInvitation req
    ) {
        var response = friendBusiness.invitationRequest(user, req);

        return Api.OK(response);
    }

    // 친구 초대 응답
    @PostMapping("/invitation/response")
    public Api<FriendResponse> invitationResponse(
            @UserSession User user,
            @RequestBody FriendInvitation req
    ) {
        var response = friendBusiness.invitationResponse(user, req);

        return Api.OK(response);
    }

    // 친구 리스트
    @GetMapping("/list")
    public Api<List<FriendResponse>> getFriendList(
            @UserSession User user
    ) {
        var response = friendBusiness.getFriendList(user);

        return Api.OK(response);
    }

    // 차단 리스트
    @GetMapping("/block/list")
    public Api<List<FriendResponse>> getBlockList(
            @UserSession User user
    ) {
        var response = friendBusiness.getBlockList(user);

        return Api.OK(response);
    }


    // 자신에게 초대를 보낸 리스트 출력
    @GetMapping("/invitation/list")
    public Api<List<FriendResponse>> getInvitationList(
            @UserSession User user
    ){
        var response = friendBusiness.findByInvitationRequest(user);

        return Api.OK(response);
    }

    // 친구 삭제, 차단 요청
    @PostMapping("/status")
    public Api<FriendResponse> statusSetting(
            @UserSession User user,
            @RequestParam String toUserId,
            @RequestParam FriendStatus status
    ) {
        var response = friendBusiness.statusSettings(user, toUserId, status);

        return Api.OK(response);
    }


}
