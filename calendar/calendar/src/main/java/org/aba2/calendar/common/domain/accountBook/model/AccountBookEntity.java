package org.aba2.calendar.common.domain.accountBook.model;

import jakarta.persistence.*;
import lombok.*;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@DynamicUpdate
@Table(name = "account_book")
public class AccountBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // 수입/지출 내역

    private Integer amount; // 수입/지출 금액

    //IncomeExpense.INCOME, IncomeExpense.EXPENSE로 저장한다.
    @Enumerated(EnumType.STRING)
    private IncomeExpense incomeExpense; // 수입/지출 판단

    private LocalDate date; // 수입/지출 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void updateAcctBook(String description, Integer amount, LocalDate date, IncomeExpense incomeExpense) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.incomeExpense = incomeExpense;
    }
}