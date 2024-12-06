package org.aba2.calendar.common.domain.accountBook.service;

import jakarta.transaction.Transactional;
import org.aba2.calendar.common.domain.accountBook.db.AccountBookRepository;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountBookServiceTest {

    @Autowired
    private AccountBookService accountBookService;

    @Test
    void handleAcctBookSaveOrUpdate() {
        // UserId를 가정
        String userId = "sjhoon1212!";

        // 오늘 날짜부터 10일간
        LocalDate startDate = LocalDate.now();

        // 10일간 10개씩 가계부 저장
        for (int dayOffset = 0; dayOffset < 10; dayOffset++) {
            LocalDate date = startDate.plusDays(dayOffset); // 각 날짜 생성

            for (int i = 0; i < 10; i++) {
                // AccountBookFormRequest 생성
                AccountBookFormRequest request = AccountBookFormRequest.builder()
                        .description("Test Description " + i)
                        .amount((i + 1) * 1000) // 예시로 금액을 설정
                        .date(date)
                        .incomeExpense(i % 2 == 0 ? "+" : "-") // 수입과 지출을 번갈아 설정
                        .build();

                // save or update 처리
                accountBookService.handleAcctBookSaveOrUpdate(request, userId);
            }
        }
    }

    @Test
    void deleteAcctBook() {
    }
}