package org.aba2.calendar.common.domain.groupuser.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserResponse;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupStatus;
import org.aba2.calendar.common.domain.groupuser.service.GroupUserConverter;
import org.aba2.calendar.common.domain.groupuser.service.GroupUserService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class GroupUserBusiness {

    private final GroupUserService groupUserService;
    private final GroupUserConverter groupUserConverter;


    // 방 만들 때 어드민으로 생성하기
    public void initialization(String groupId, String userId) {
        groupUserService.initialization(groupId, userId);
    }

    // 초대하기
    public GroupUserResponse invitationUser(String groupId, String userId) {
        var groupUserId = groupUserService.getGroupUserId(groupId, userId);

        var response = groupUserService.invitationUser(groupUserId);

        return groupUserConverter.toResponse(response);
    }

    // TODO 초대 응하기


    // 탈퇴, 나가기
    public GroupUserResponse toLeaveGroup(String groupId, String userId, GroupStatus status) {
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
