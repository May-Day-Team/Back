package org.aba2.calendar.common.domain.group.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.group.business.GroupBusiness;
import org.aba2.calendar.common.domain.group.model.GroupCreateRequest;
import org.aba2.calendar.common.domain.group.model.GroupResponse;
import org.aba2.calendar.common.domain.user.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupBusiness groupBusiness;

    @PostMapping("/create")
    public Api<GroupResponse> create(
            @RequestBody
            GroupCreateRequest req,
            @UserSession User user
    ) {
        var response = groupBusiness.create(req, user);
        return Api.OK(response);
    }


    @DeleteMapping("/delete/{groupId}")
    public Api<String> delete(
            @PathVariable String groupId
    ) {
        groupBusiness.deleteGroup(groupId);

        return Api.OK("그룹이 삭제되었습니다.");
    }

}
