package org.aba2.calendar.common.domain.accountBook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.accountBook.db.AccountBookRepository;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository acctBookRepository;
    private final UserService userService;

    // 가계부 리스트 조회 - 페이지
    public Page<AccountBookEntity> findAllByUIDAcctBookList(String userId, int page) {
        Pageable pageable = PageRequest.of(page, 10); // 페이지네이션만 설정
        return acctBookRepository.findAllByUser_idOrderByDateDesc(userId, pageable);
    }

    // 생성/수정 핸들
    @Transactional
    public void handleAcctBookSaveOrUpdate(AccountBookFormRequest request, String userId) {
        if (acctBookRepository.findById(request.getId()).isPresent()) {
            updateAcctBook(request);
        } else {
            createAcctBook(request, userId);
        }
    }

    // 생성하기
    private void createAcctBook(AccountBookFormRequest request, String userId) {

        // user객체 넣어줘야함
        UserEntity user = userService.findByIdWithThrow(userId);

        // 기존 가계부 존재 여부 확인
        AccountBookEntity newAcctBook = AccountBookEntity.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .date(request.getDate())
                .incomeExpense(request.getIncomeExpense())
                .user(user)
                .build();
        acctBookRepository.save(newAcctBook);
    }

    // 수정하기
    private void updateAcctBook(AccountBookFormRequest request) {
        // 기존 가계부 존재 여부 확인
        AccountBookEntity acctBook = findByAcctBookIdWithThrow(request.getId());

        //기존 가계부의 필드 수정 - dirtyCheck
        acctBook.updateAcctBook(
                request.getDescription(),
                request.getAmount(),
                request.getDate(),
                request.getIncomeExpense()
        );
    }

    // 가계부 삭제
    public void deleteAcctBook(Long id){
        acctBookRepository.delete(findByAcctBookIdWithThrow(id));
    }

    // 특정 날짜의 가계부 '목록' 찾기
    public List<AccountBookEntity> findByUIDAndDateWithThrow(String userId, LocalDate date) {
        return acctBookRepository.findByUser_idAndDate(userId, date)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 날짜엔 수입과 지출이 없습니다"));
    }

    // 특정 ID를 가진 가게부 '1개' 찾기
    public AccountBookEntity findByAcctBookIdWithThrow(Long id) {
        return acctBookRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 내역은 존재하지 않습니다."));
    }
}
