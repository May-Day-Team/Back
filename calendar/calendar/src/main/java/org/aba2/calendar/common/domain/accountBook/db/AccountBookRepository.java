package org.aba2.calendar.common.domain.accountBook.db;

import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

    // 사용자 ID 기준으로 가계부 리스트 반환
    List<AccountBookEntity> findAllByUser_UserIdOrderByDateDesc(String userId);

    // 사용자 ID와 날짜 기준으로 가계부 리스트 찾기
    Optional<List<AccountBookEntity>> findAllByUser_UserIdAndDateOrderByIdAsc(String userId, LocalDate date);
}
