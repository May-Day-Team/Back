package org.aba2.calendar.common.domain.group.db;

import org.aba2.calendar.common.domain.group.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {


}
