package org.aba2.calendar.common.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.user.db.UserRepository;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.errorcode.UserErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserEntity register(UserEntity data) {

        // ID 중복 체크
        if (userRepository.existsById(data.getUserId())) {
            throw new ApiException(UserErrorCode.DUPLICATE_USER_ID);
        }

        // 암호화
        data.setPassword(passwordEncoder.encode(data.getPassword()));

        // 저장하고 return 하기
        return userRepository.save(data);
    }

    // 로그인
    public UserEntity login(String userId, String password) {

        var entity = findByIdWithThrow(userId);

        // 비밀번호가 같지 않다면 로그인 에러 발생
        if (!passwordEncoder.matches(password, entity.getPassword())) {
            throw new ApiException(UserErrorCode.LOGIN_FAILED);
        }

        return entity;
    }

    public UserEntity findByIdWithThrow(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "사용자를 찾을 수 없습니다."));
    }
}
