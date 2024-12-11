package org.aba2.calendar.common.domain.accountBook.buisiness;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.accountBook.dto.AccountBookFormRequest;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookConverter;
import org.aba2.calendar.common.domain.accountBook.service.AccountBookService;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.record.service.RecordConverter;
import org.aba2.calendar.common.domain.record.service.RecordService;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Objects;

@Business
@RequiredArgsConstructor
public class AccountBookBusiness {

    private final AccountBookService accountBookService;

    private final AccountBookConverter accountBookConverter;

    // 가계부 작성,수정
    public void saveAccountBookResponse(User user, AccountBookFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        accountBookService.handleAcctBookSaveOrUpdate(request, user.getId());
    }

    // 가계부 삭제
    public String deleteAccountBookResponse(User user, AccountBookFormRequest request){
        if (Objects.isNull(user) || user.getId() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        accountBookService.deleteAcctBook(user.getId(), request.getId());

        return "AccountBook deleted successfully";
    }
}
