package org.aba2.calendar.common.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.group.db.GroupRepository;
import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.aba2.calendar.common.domain.user.db.UserRepository;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    // 그룹 생성하기
    public GroupEntity create(String groupName, String url) {
        return GroupEntity.builder()
                .groupId(UUID.randomUUID().toString())
                .groupName(groupName)
                .profileUrl(url) // TODO URL 기본 이미지 만들기
                .build();
    }

    // 그룹 찾기
    public GroupEntity findByIdWithThrow(String groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "해당 그룹을 찾을 수 없음"));
    }
}
