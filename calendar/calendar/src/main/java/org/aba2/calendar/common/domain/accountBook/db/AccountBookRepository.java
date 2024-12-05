package org.aba2.calendar.common.domain.accountBook.db;

import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {


    // 사용자 ID 기준으로 가계부 리스트 반환
    List<AccountBookEntity> findAllByUser_UserIdOrderByDateDesc(String userId);

    // 사용자 ID 기준으로 가계부 리스트 페이징 처리 (최신순)
//    Page<AccountBookEntity> findAllByUser_idOrderByDateDesc(String userId, Pageable pageable);

    // 사용자 ID와 날짜 기준으로 가계부 리스트 찾기
    List<AccountBookEntity> findAllByUser_UserIdAndDateOrderByIdDesc(String userId, LocalDate date);
}
