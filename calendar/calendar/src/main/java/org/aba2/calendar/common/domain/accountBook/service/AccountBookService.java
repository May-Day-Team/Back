package org.aba2.calendar.common.domain.accountBook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.accountBook.db.AccountBookRepository;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.AccountBookErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository acctBookRepository;
    private final UserService userService;

    // UID+특정 날짜로 가계부 리스트 조회
    public List<AccountBookEntity> getUIDAndDateWithThrow(String userId, LocalDate date) {
        return acctBookRepository.findAllByUser_UserIdAndDateOrderByIdAsc(userId, date)
                .orElseThrow(() -> new ApiException(AccountBookErrorCode.ACCOUNT_BOOK_NOT_FOUND, "해당 날짜엔 수입과 지출이 없습니다"));
    }
    
    // UID로 해당 유저가 작성한 모든 가계부 조회
    public List<AccountBookEntity> getUIDWithThrow(String userId) {
        return acctBookRepository.findAllByUser_UserIdOrderByDateDesc(userId)
                .orElseThrow(() -> new ApiException(AccountBookErrorCode.ACCOUNT_BOOK_NOT_FOUND, "작성한 가계부가 없습니다"));
    }

    // UID로 가계부 리스트 조회 + 날짜별 그룹화 및 합산
//    public Page<DateTotalDTO> getPagedDateTotals(String userId, int page) {
//        // 모든 데이터 조회 (페이지네이션 없이)
//        List<AccountBookEntity> accountBooks = acctBookRepository.findAllByUser_UserIdOrderByDateDesc(userId);
//
//        // 날짜별 그룹화 및 합산
//        Map<LocalDate, Integer> dateTotals = accountBooks.stream()
//                .collect(Collectors.groupingBy(
//                        AccountBookEntity::getDate, // 날짜 기준으로 그룹화
//                        Collectors.summingInt(account -> {
//                            // 손익(+/-)을 반영하여 합산
//                            return account.getIncomeExpense() == IncomeExpense.INCOME
//                                    ? account.getAmount()
//                                    : -account.getAmount();
//                        })
//                ));
//
//        // DTO로 변환 및 정렬
//        List<DateTotalDTO> totalList = dateTotals.entrySet().stream()
//                .map(entry -> new DateTotalDTO(entry.getKey(), entry.getValue()))
//                .sorted(Comparator.comparing(DateTotalDTO::getDate).reversed()) // 날짜 내림차순 정렬
//                .collect(Collectors.toList());
//
//        // 페이지네이션 적용
//        Pageable pageable = PageRequest.of(page, 10); // 페이지 크기 10
//
//        // PageImpl을 사용하여 페이지네이션 처리
//        return new PageImpl<>(totalList, pageable, totalList.size());
//    }

    // 생성/수정 핸들
    @Transactional
    public void handleAcctBookSaveOrUpdate(AccountBookFormRequest request, String userId) {
        if (request.getId()!=null && acctBookRepository.existsById(request.getId())) {
            updateAcctBook(userId, request);
        }
        else {
            createAcctBook(userId, request);
        }
    }

    // 생성하기
    private void createAcctBook(String userId, AccountBookFormRequest request) {

        // user객체 넣어줘야함
        UserEntity user = userService.findByIdWithThrow(userId);

        // +나 -문자열로 오기 때문에 이걸로 형변환을 해준다.
        IncomeExpense incomeExpense = IncomeExpense.get(request.getIncomeExpense());

        // 기존 가계부 존재 여부 확인
        AccountBookEntity newAcctBook = AccountBookEntity.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .date(request.getDate())
                .incomeExpense(incomeExpense)
                .user(user)
                .build();
        acctBookRepository.save(newAcctBook);
    }

    // 수정하기
    private void updateAcctBook(String userId, AccountBookFormRequest request) {
        // 기존 가계부 존재 여부 확인
        AccountBookEntity entity = findByAcctBookIdWithThrow(request.getId());

        // 가계부 삭제 권한(작성자)여부 확인
        validateUserAuthorization(userId, entity);

        // +나 -문자열로 오기 때문에 이걸로 형변환을 해준다.
        IncomeExpense incomeExpense = IncomeExpense.get(request.getIncomeExpense());

        //기존 가계부의 필드 수정 - dirtyCheck
        entity.updateAcctBook(
                request.getDescription(),
                request.getAmount(),
                request.getDate(),
                incomeExpense
        );
    }

    // 가계부 삭제
    public void deleteAcctBook(String userId, Long accountBookId) {
        // 기존 가계부 존재 여부 확인
        AccountBookEntity entity = findByAcctBookIdWithThrow(accountBookId);
        
        // 가계부 삭제 권한(작성자)여부 확인
        validateUserAuthorization(userId, entity);
        
        // 삭제
        acctBookRepository.delete(findByAcctBookIdWithThrow(accountBookId));
    }

    // 특정 ID를 가진 가게부 '1개' 찾기
    public AccountBookEntity findByAcctBookIdWithThrow(Long accountBookId) {
        return acctBookRepository.findById(accountBookId)
                .orElseThrow(() -> new ApiException(AccountBookErrorCode.ACCOUNT_BOOK_NOT_FOUND, "해당 가계부를 찾을 수 없습니다."));
    }

    // 권한 조회
    private void validateUserAuthorization(String userId, AccountBookEntity entity) {
        if (!entity.getUser().getUserId().equals(userId)) {
            throw new ApiException(AccountBookErrorCode.UNAUTHORIZED_ACCESS, "권한이 없습니다.");
        }
    }
}
