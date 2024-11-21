package org.aba2.calendar.common.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.user.db.UserRepository;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.errorcode.UserErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public UserEntity register(UserEntity data) {

        // ID 중복 체크
        if (userRepository.existsById(data.getUserId())) {
            throw new ApiException(UserErrorCode.DUPLICATE_USER_ID);
        }

        // 저장하고 return 하기
        return userRepository.save(data);
    }

    // 로그인
    public UserEntity login(String userId, String password) {
        // 아이디로 DB에 존재하는 유저 가지고 오기
        var optionalUser = userRepository.findById(userId);

        // 비어있다면 (아이디가 맞지 않음) 로그인 에러 발생하기
        if (optionalUser.isEmpty()) {
            throw new ApiException(UserErrorCode.LOGIN_FAILED);
        }

        var entity = optionalUser.get();
        // 비밀번호가 같지 않다면 로그인 에러 발생
        if (!password.equals(entity.getPassword())) {
            throw new ApiException(UserErrorCode.LOGIN_FAILED);
        }


        // 임시로 확인을 위해 entity
        return entity;
    }


    public UserEntity getUserWithThrow(String userId) {
        if (userId == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "뭔 오류냐 시발");
        }

        var userEntity = userRepository.findById(userId);

        return Optional.of(userEntity)
                .map(it -> {
                    return userEntity.get();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

    }

    public UserEntity findByIdWithThrow(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "사용자를 찾을 수 없습니다."));
    }
}
