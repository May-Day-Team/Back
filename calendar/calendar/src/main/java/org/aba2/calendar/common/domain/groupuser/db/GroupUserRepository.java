package org.aba2.calendar.common.domain.groupuser.db;

import org.aba2.calendar.common.domain.groupuser.model.GroupUserEntity;
import org.aba2.calendar.common.domain.groupuser.model.GroupUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUserEntity, GroupUserId> {

    List<GroupUserEntity> findAllByGroupId(String groupId);

}
