package org.aba2.calendar.common.domain.accountBook.service;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookResponse;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;
import org.aba2.calendar.common.domain.record.model.RecordEntity;

@Converter
public class AccountBookConverter {
    // AccountBookFormRequest -> AccountBookEntity 변환
    public AccountBookResponse toEntity(AccountBookFormRequest request) {
        return AccountBookResponse.builder()
                .accountBookId(request.getAccountBookId())
                .description(request.getDescription())
                .incomeExpense(IncomeExpense.get(request.getIncomeExpense()))
                .amount(request.getAmount())
                .date(request.getDate())
                .build();
    }

    // AccountBookEntity -> AccountBookResponse 변환
    public AccountBookResponse toResponse(AccountBookEntity entity) {
        return AccountBookResponse.builder()
                .accountBookId(entity.getAccountBookId())
                .description(entity.getDescription())
                .incomeExpense(entity.getIncomeExpense())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .build();
    }
}
