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

    // 그룹 생성하기 (자동으로 권한 어드민)
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


    // 역할 변경
    public GroupUserEntity roleChange(GroupUserId id, GroupRole role) {

        // todo 요청하는 사람이 권한이 있는지 확인하기

        var entity = findByIdWithThrow(id);

        entity.setRole(role);
        return groupUserRepository.save(entity);
    }

    // 해당 그룹의 일정 리스트 제공
    public List<GroupUserEntity> findAllByGroupId(String groupId) {
        return groupUserRepository.findAllByGroupId(groupId);
    }


    // 그룹 찾기
    private GroupUserEntity findByIdWithThrow(GroupUserId id) {
        return groupUserRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

    // GroupUserId 제공
    public GroupUserId getGroupUserId(String groupId, String userId) {
        return GroupUserId.builder()
                .groupId(groupId)
                .userId(userId)
                .build();
    }

    // 유저 초대하기
    public GroupUserEntity invitationUser(GroupUserId groupUserId) {
        // 이미 그룹에 존재하는지
        if (groupUserRepository.existsById(groupUserId)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "이미 초대한 유저입니다.");
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
