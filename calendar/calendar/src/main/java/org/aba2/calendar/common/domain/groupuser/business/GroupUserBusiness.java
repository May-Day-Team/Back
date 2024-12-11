package org.aba2.calendar.common.domain.groupuser.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserEntity;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserInitialization;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserResponse;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupStatus;
import org.aba2.calendar.common.domain.groupuser.service.GroupUserConverter;
import org.aba2.calendar.common.domain.groupuser.service.GroupUserService;
import org.aba2.calendar.common.domain.user.model.User;

import java.util.List;

@Business
@RequiredArgsConstructor
public class GroupUserBusiness {

    private final GroupUserService groupUserService;
    private final GroupUserConverter groupUserConverter;


    // 방 만들 때 어드민으로 생성하기
    public GroupUserResponse initialization(String groupId, String userId) {
        var response = groupUserService.initialization(groupId, userId);

        return groupUserConverter.toResponse(response);
    }

    // 초대하기
    public GroupUserResponse invitationUser(GroupUserInitialization request, User user) {
        var response = groupUserService.invitationUser(request.getGroupId(), request.getUserId());

        return groupUserConverter.toResponse(response);
    }

    // 상태 변경하기
    public GroupUserResponse statusChange(String groupId, String userId, GroupStatus status) {
        var groupUserId = groupUserService.getGroupUserId(groupId, userId);

        var response = groupUserService.statusChange(groupUserId, status);

        return groupUserConverter.toResponse(response);
    }


    // 그룹원 리스트 출력
    public List<GroupUserResponse> findAllByGroupId(String groupId) {
        return groupUserService.findAllByGroupId(groupId).stream()
                .map(groupUserConverter::toResponse)
                .toList()
                ;
    }

}
