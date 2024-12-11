package org.aba2.calendar.common.domain.group.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.group.model.GroupCreateRequest;
import org.aba2.calendar.common.domain.group.model.GroupResponse;
import org.aba2.calendar.common.domain.group.model.GroupUpdateRequest;
import org.aba2.calendar.common.domain.group.service.GroupConverter;
import org.aba2.calendar.common.domain.group.service.GroupService;
import org.aba2.calendar.common.domain.groupuser.business.GroupUserBusiness;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

@Business
@RequiredArgsConstructor
public class GroupBusiness {

    private final GroupService groupService;
    private final GroupConverter groupConverter;
    private final GroupUserBusiness groupUserBusiness;


    // 그룹 생성하기
    public GroupResponse create(GroupCreateRequest req, User user) {
        var groupName = req.getGroupName();
        var userId = user.getId();
        var url = req.getProfileUrl();

        if (groupName == null || userId == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        // url == null 이라면 기본 url
        if (url == null) {
            url = "default.png"; // todo 기본 url
        }

        var groupEntity = groupService.create(groupName, url);

        // 그룹을 생성할 때 방장으로 권한 설정
        groupUserBusiness.initialization(groupEntity.getGroupId(), groupEntity.getGroupId());

        return groupConverter.toResponse(groupEntity);
    }


    // 그룹 정보 수정하기
    public GroupResponse updateGroupInfo(GroupUpdateRequest groupUpdateRequest) {

        // TODO 요청하는 사람 권한 확인하기

        var response = groupService.updateGroup(groupUpdateRequest);

        return groupConverter.toResponse(response);
    }


    // 그룹 삭제하기
    public void deleteGroup(String groupId) {

        groupService.deleteGroup(groupId);

    }


}
