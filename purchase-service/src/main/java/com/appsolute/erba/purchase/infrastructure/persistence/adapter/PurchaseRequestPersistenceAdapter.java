package com.appsolute.erba.purchase.infrastructure.persistence.adapter;

import com.appsolute.erba.purchase.domain.model.PurchaseRequest;
import com.appsolute.erba.purchase.domain.port.PurchaseRequestRepository;
import com.appsolute.erba.purchase.domain.valueobject.PurchaseRequestStatus;
import com.appsolute.erba.purchase.infrastructure.persistence.entity.PurchaseRequestJpaEntity;
import com.appsolute.erba.purchase.infrastructure.persistence.repository.SpringDataPurchaseRequestJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PurchaseRequestPersistenceAdapter implements PurchaseRequestRepository {

    private final SpringDataPurchaseRequestJpaRepository jpaRepository;

    public PurchaseRequestPersistenceAdapter(
            SpringDataPurchaseRequestJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PurchaseRequest save(PurchaseRequest purchaseRequest) {
        PurchaseRequestJpaEntity savedEntity =
                jpaRepository.save(toEntity(purchaseRequest));

        return toDomain(savedEntity);
    }

    @Override
    public Optional<PurchaseRequest> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Page<PurchaseRequest> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(this::toDomain);
    }

    @Override
    public Page<PurchaseRequest> findByRequesterUserId(
            UUID requesterUserId,
            Pageable pageable
    ) {
        return jpaRepository
                .findByRequesterUserIdOrderByCreatedAtDesc(requesterUserId, pageable)
                .map(this::toDomain);
    }

    @Override
    public Page<PurchaseRequest> findByStatus(
            PurchaseRequestStatus status,
            Pageable pageable
    ) {
        return jpaRepository
                .findByStatusOrderByCreatedAtDesc(status.name(), pageable)
                .map(this::toDomain);
    }

    @Override
    public Page<PurchaseRequest> findByRequesterUserIdAndStatus(
            UUID requesterUserId,
            PurchaseRequestStatus status,
            Pageable pageable
    ) {
        return jpaRepository
                .findByRequesterUserIdAndStatusOrderByCreatedAtDesc(
                        requesterUserId,
                        status.name(),
                        pageable
                )
                .map(this::toDomain);
    }

    private PurchaseRequestJpaEntity toEntity(PurchaseRequest purchaseRequest) {
        return new PurchaseRequestJpaEntity(
                purchaseRequest.getId(),
                purchaseRequest.getRequesterUserId(),
                purchaseRequest.getProductName(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getDescription(),
                purchaseRequest.getStatus().name(),
                purchaseRequest.getReviewedByUserId(),
                purchaseRequest.getReviewedAt(),
                purchaseRequest.getReviewNote(),
                purchaseRequest.getCreatedAt(),
                purchaseRequest.getUpdatedAt()
        );
    }

    private PurchaseRequest toDomain(PurchaseRequestJpaEntity entity) {
        return PurchaseRequest.restore(
                entity.getId(),
                entity.getRequesterUserId(),
                entity.getProductName(),
                entity.getQuantity(),
                entity.getDescription(),
                PurchaseRequestStatus.valueOf(entity.getStatus()),
                entity.getReviewedByUserId(),
                entity.getReviewedAt(),
                entity.getReviewNote(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}