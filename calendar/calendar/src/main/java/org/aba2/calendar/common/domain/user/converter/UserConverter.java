package org.aba2.calendar.common.domain.user.converter;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.domain.user.model.UserResponse;

import java.time.LocalDateTime;

@Converter
public class UserConverter {

    // entity(dao) -> response(dto)
    public UserResponse toResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getUserId())
                .password(entity.getPassword())
                .name(entity.getName())
                .email(entity.getEmail())
                .birthdate(entity.getBirthdate())
                .phoneNumber(entity.getPhoneNumber())
                .createAt(entity.getCreateAt())
                .build();
    }

    // UserRegisterRequest -> UserEntity
    public UserEntity toEntity(UserRegisterRequest dto) {
        return UserEntity.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .phoneNumber(dto.getPhoneNumber())
                .createAt(LocalDateTime.now())
                .build()
                ;
    }

}
