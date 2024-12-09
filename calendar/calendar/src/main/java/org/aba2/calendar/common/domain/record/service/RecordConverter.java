package org.aba2.calendar.common.domain.record.service;

import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.record.dto.RecordFormRequest;
import org.aba2.calendar.common.domain.record.dto.RecordResponse;
import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.model.RecordId;
import org.aba2.calendar.common.domain.record.model.enums.Weather;

@Converter
public class RecordConverter {
    // RecordFormRequest -> RecordEntity 변환
    public RecordEntity toEntity(RecordFormRequest request, String userId) {
        return RecordEntity.builder()
                .recordId(new RecordId(request.getCreateAt(), userId))
                .title(request.getTitle())
                .content(request.getContent())
                .weather(Weather.get(request.getWeather()))
                .build();
    }

    // RecordEntity -> RecordResponse 변환
    public RecordResponse toResponse(RecordEntity entity) {
        return RecordResponse.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .weather(entity.getWeather())
                .updateAt(entity.getUpdateAt())
                .build();
    }
}