package org.aba2.calendar.common.domain.user.db;


import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {


}
