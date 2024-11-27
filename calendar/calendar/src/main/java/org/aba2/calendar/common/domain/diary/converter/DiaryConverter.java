package org.aba2.calendar.common.domain.diary.converter;

import org.aba2.calendar.common.annotation.Converter;

@Converter
public class DiaryConverter {

    // entity(dao) -> response(dto)
//    public UserResponse toResponse(DiaryEntity entity) {
//        return UserResponse.builder()
//                .id(entity.getDiaryId())
//                .title(entity.getTitle())
//                .content(entity.getContent())
//                .tag(entity.getTag())
//                .weather(entity.getWeather())
//                .createAt(entity.getCreateAt())
//                .updateAt(entity.getUpdateAt())
//                .build();
//    }

    // DiaryCreateRequest -> DiaryEntity
//    public DiaryEntity toEntity(DiaryCreateRequest dto) {
//        return DiaryEntity.builder()
//                .title(dto.getTitle())
//                .content(dto.getContent())
//                .tag(dto.getTag())
//                .weather(dto.getWeather())
//                .createAt(LocalDate.now())
//                .build()
//                ;
//    }

}
