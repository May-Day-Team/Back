package org.aba2.calendar.common.domain.record.db;

import org.aba2.calendar.common.domain.record.model.RecordEntity;
import org.aba2.calendar.common.domain.record.model.RecordId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, RecordId> {
    boolean existsByRecordId(RecordId recordId);
    Optional<RecordEntity> findByRecordId(RecordId recordId);
    Page<RecordEntity> findAllByRecordId_UserIdOrderByRecordId_CreateAtDesc(String userId, Pageable pageable);
}
