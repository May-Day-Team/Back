package org.aba2.calendar.common.domain.accountBook.buisiness;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookDetailResponse;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookTotalResponse;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookConverter;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class AccountBookBusiness {

    private final AccountBookService accountBookService;

    private final AccountBookConverter accountBookConverter;

    public List<AccountBookTotalResponse> getAccountBookList(String userId) {
        List<AccountBookEntity> accountBookList = accountBookService.getUIDWithThrow(userId);

        Map<LocalDate, Integer> dateSumMap = new HashMap<>();
        for(AccountBookEntity accountBook : accountBookList) {
            LocalDate entityDate = accountBook.getDate();
            int amount = accountBook.getAmount();

            //IncomeExpense에 따라 금액을 더하거나 뺸다.
            if(accountBook.getIncomeExpense()== IncomeExpense.EXPENSE){
                amount = -amount;
            }

            //같은 날짜가 있으면 합산하고, 없으면 새로 추가
            dateSumMap.put(entityDate, dateSumMap.getOrDefault(entityDate, 0) + amount);
        }

        // 3. 합산된 결과를 Response로 변환
        List<AccountBookTotalResponse> responseList = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : dateSumMap.entrySet()) {
            responseList.add(new AccountBookTotalResponse(entry.getKey(), entry.getValue()));
        }

        // 4. 날짜 역순으로 정렬 (date 역순 정렬)
        responseList.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate()));

        return responseList;
    }

    public List<AccountBookDetailResponse> getAccountBookDetail(String userId, LocalDate date) {
        List<AccountBookEntity> uidAndDateWithThrow = accountBookService.getUIDAndDateWithThrow(userId, date);

        return uidAndDateWithThrow.stream()
                .map(accountBookConverter::toResponse) // AccountBookEntity -> AccountBookResponse 변환
                .collect(Collectors.toList()); // 리스트로 수집
    }


    // 가계부 작성,수정
    public void saveAccountBookResponse(User user, AccountBookFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        accountBookService.handleAcctBookSaveOrUpdate(request, user.getId());
    }

    // 가계부 삭제
    public void deleteAccountBookResponse(User user, AccountBookFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        accountBookService.deleteAcctBook(user.getId(), request.getId());
    }
}
