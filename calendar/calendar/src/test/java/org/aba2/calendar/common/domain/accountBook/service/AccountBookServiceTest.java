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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountBookServiceTest {

    @Autowired
    private AccountBookService accountBookService;

    @Test
    void handleAcctBookSaveOrUpdate() {
        // UserId를 가정
        String userId = "testUser99";

        // 오늘 날짜부터 10일간
        LocalDate startDate = LocalDate.now();

        Random random = new Random();  // Random 객체 생성

        for (int dayOffset = 0; dayOffset < 10; dayOffset++) {
            LocalDate date = startDate.plusDays(dayOffset); // 각 날짜 생성

            for (int i = 0; i < 2; i++) {
                // 랜덤하게 수입/지출을 결정
                String incomeExpense = random.nextBoolean() ? "+" : "-"; // 수입(+) 또는 지출(-) 랜덤 설정

                // 랜덤한 금액 생성 (예: 1000~10000 사이의 금액)
                int amount = (random.nextInt(10) + 1) * 1000;  // 1000 ~ 10000 사이의 금액

                // AccountBookFormRequest 생성
                AccountBookFormRequest request = AccountBookFormRequest.builder()
                        .description("Test Description " + i)
                        .amount(amount)
                        .date(date)
                        .incomeExpense(incomeExpense) // 랜덤으로 수입/지출 설정
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