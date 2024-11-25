package org.aba2.calendar.common.domain.groupuser.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.groupuser.db.GroupUserRepository;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserEntity;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserId;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupRole;
import org.aba2.calendar.common.domain.groupuser.model.enums.GroupStatus;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupUserService {

    private final GroupUserRepository groupUserRepository;


    public void initialization(String groupId, String userId) {
        var entity = GroupUserEntity.builder()
                .groupId(groupId)
                .userId(userId)
                .role(GroupRole.ADMIN)
                .status(GroupStatus.ACCEPTANCE)
                .build()
                ;

        groupUserRepository.save(entity);
    }

    // 상태 변화
    public GroupUserEntity statusChange(GroupUserId id, GroupStatus status) {
        var entity = findByIdWithThrow(id);

        entity.setStatus(status);
        return groupUserRepository.save(entity);
    }


    // 권한 변경
    public GroupUserEntity roleChange(GroupUserId id, GroupRole role) {
        var entity = findByIdWithThrow(id);

        entity.setRole(role);
        return groupUserRepository.save(entity);
    }

    public List<GroupUserEntity> findAllByGroupId(String groupId) {
        return groupUserRepository.findAllByGroupId(groupId);
    }


    private GroupUserEntity findByIdWithThrow(GroupUserId id) {
        return groupUserRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

    public GroupUserId getGroupUserId(String groupId, String userId) {
        return GroupUserId.builder()
                .groupId(groupId)
                .userId(userId)
                .build();
    }

    public GroupUserEntity invitationUser(GroupUserId groupUserId) {
        // TODO 이미 존재하고 있을 때
        if (groupUserRepository.existsById(groupUserId)) {

        }


        // 존재하지 않을 때 초대하기
        var newEntity = GroupUserEntity.builder()
                .groupId(groupUserId.getGroupId())
                .userId(groupUserId.getUserId())
                .role(GroupRole.USER)
                .status(GroupStatus.WAIT)
                .build()
                ;

        return groupUserRepository.save(newEntity);
    }
}
