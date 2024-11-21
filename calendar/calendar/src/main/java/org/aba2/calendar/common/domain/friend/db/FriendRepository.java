package org.aba2.calendar.common.domain.friend.db;

import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity,String> {

    // 친구 목록 불러오기
    @Query("SELECT f FROM FriendEntity f WHERE (f.fromUserId = :userId OR f.toUserId = :userId) AND f.status = :status")
    List<FriendEntity> findFriendsByUserId(@Param("userId") String userId, @Param("status") FriendStatus status);

}
