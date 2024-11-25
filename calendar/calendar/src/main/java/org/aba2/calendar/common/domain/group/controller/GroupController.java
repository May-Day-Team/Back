package org.aba2.calendar.common.domain.group.controller;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.group.business.GroupBusiness;
import org.aba2.calendar.common.domain.group.model.GroupCreateRequest;
import org.aba2.calendar.common.domain.group.model.GroupResponse;
import org.aba2.calendar.common.domain.group.service.GroupService;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupBusiness groupBusiness;

    @PostMapping("/create")
    public Api<GroupResponse> create(
            @RequestBody
            GroupCreateRequest req
    ) {
        var response = groupBusiness.create(req);
        return Api.OK(response);
    }

}
