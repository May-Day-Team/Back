package org.aba2.calendar.common.domain.user.db;


import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
<<<<<<< HEAD

    Optional<UserEntity> findByIdOrPhoneNumber(String id, String phoneNumber);

=======
>>>>>>> diary
}
