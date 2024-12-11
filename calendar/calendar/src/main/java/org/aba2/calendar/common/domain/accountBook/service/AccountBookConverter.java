package org.aba2.calendar.common.domain.accountBook.service;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookDetailResponse;
import org.aba2.calendar.common.domain.accountBook.model.AccountBookEntity;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;

@Converter
public class AccountBookConverter {
    // AccountBookFormRequest -> AccountBookEntity 변환
    public AccountBookDetailResponse toEntity(AccountBookFormRequest request) {
        return AccountBookDetailResponse.builder()
                .id(request.getId())
                .description(request.getDescription())
                .incomeExpense(IncomeExpense.get(request.getIncomeExpense()))
                .amount(request.getAmount())
                .date(request.getDate())
                .build();
    }

    // AccountBookEntity -> AccountBookResponse 변환
    public AccountBookDetailResponse toResponse(AccountBookEntity entity) {
        return AccountBookDetailResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .incomeExpense(entity.getIncomeExpense())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .build();
    }
}
