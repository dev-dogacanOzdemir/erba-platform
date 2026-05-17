package com.appsolute.erba.purchase.infrastructure.persistence.repository;

import com.appsolute.erba.purchase.infrastructure.persistence.entity.PurchaseRequestJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPurchaseRequestJpaRepository
        extends JpaRepository<PurchaseRequestJpaEntity, UUID> {

    Page<PurchaseRequestJpaEntity> findByRequesterUserIdOrderByCreatedAtDesc(
            UUID requesterUserId,
            Pageable pageable
    );

    Page<PurchaseRequestJpaEntity> findByStatusOrderByCreatedAtDesc(
            String status,
            Pageable pageable
    );

    Page<PurchaseRequestJpaEntity> findByRequesterUserIdAndStatusOrderByCreatedAtDesc(
            UUID requesterUserId,
            String status,
            Pageable pageable
    );
}