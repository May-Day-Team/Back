package org.aba2.calendar.common.domain.accountBook.db;

import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

    // 사용자 ID 기준으로 가계부 리스트 페이징 처리 (최신순)
    Page<AccountBookEntity> findAllByUser_idOrderByDateDesc(String userId, Pageable pageable);

    // 사용자 ID와 날짜 기준으로 가계부 리스트 찾기
    Optional<List<AccountBookEntity>> findByUser_idAndDate(String userId, LocalDate date);
}
