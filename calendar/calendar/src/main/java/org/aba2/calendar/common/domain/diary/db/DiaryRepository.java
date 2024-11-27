package org.aba2.calendar.common.domain.diary.db;

import org.aba2.calendar.common.domain.diary.model.DiaryEntity;
import org.aba2.calendar.common.domain.diary.model.DiaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, DiaryId> {
    Optional<DiaryEntity> findByDiaryId(DiaryId diaryId);
    List<DiaryEntity> findByUser_UserId(String userId);

}
