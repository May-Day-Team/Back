package org.aba2.calendar.common.domain.groupuser.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.groupuser.business.GroupUserBusiness;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserInitialization;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group/user")
public class GroupUserApiController {

    private final GroupUserBusiness groupUserBusiness;


    @PostMapping("/create")
    public Api<GroupUserResponse> create(
            @UserSession
            User user,
            @RequestBody
            GroupUserInitialization request
    ) {
        System.out.println(user);
        System.out.println(request);
        var response = groupUserBusiness.invitationUser(request, user);

        return Api.OK(response);
    }

}
