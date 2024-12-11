package org.aba2.calendar.common.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.group.db.GroupRepository;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.group.model.GroupUpdateRequest;
import org.aba2.calendar.common.domain.user.db.UserRepository;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;


    // 그룹 생성하기
    public GroupEntity create(String groupName, String url) {
        var entity = GroupEntity.builder()
                .groupId(UUID.randomUUID().toString())
                .groupName(groupName)
                .profileUrl(url)
                .createAt(LocalDateTime.now())
                .build();

        return groupRepository.save(entity);
    }

    // 그룹 찾기
    public GroupEntity findByIdWithThrow(String groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "해당 그룹을 찾을 수 없음"));
    }


    // 그룹 삭제하기
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }


    // 그룹 수정하기
    public GroupEntity updateGroup(GroupUpdateRequest groupUpdateRequest) {
        var groupId = groupUpdateRequest.getGroupId();

        var entity = findByIdWithThrow(groupId);

        entity.setGroupName(groupUpdateRequest.getGroupName());
        entity.setGroupName(groupUpdateRequest.getGroupName());
        entity.setProfileUrl(groupUpdateRequest.getProfileUrl());

        return groupRepository.save(entity);
    }


}
