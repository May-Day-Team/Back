package org.aba2.calendar.common.domain.group.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.group.model.GroupCreateRequest;
import org.aba2.calendar.common.domain.group.model.GroupResponse;
import org.aba2.calendar.common.domain.group.service.GroupConverter;
import org.aba2.calendar.common.domain.group.service.GroupService;
import org.aba2.calendar.common.domain.groupuser.business.GroupUserBusiness;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

@Business
@RequiredArgsConstructor
public class GroupBusiness {

    private final GroupService groupService;
    private final GroupConverter groupConverter;
    private final GroupUserBusiness groupUserBusiness;


    // 그룹 생성하기
    public GroupResponse create(GroupCreateRequest req) {
        var groupName = req.getGroupName();
        var userId = req.getUserId();
        var url = req.getProfileUrl();

        if (groupName == null || userId == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        // url == null 이라면 기본 url
        if (url == null) {
            url = ""; // todo 기본 url
        }

        var response = groupService.create(groupName, url);

        // 그룹을 생성할 때 방장으로 권한 설정
        groupUserBusiness.initialization(response.getGroupId(), userId);

        return groupConverter.toResponse(response);
    }

}
