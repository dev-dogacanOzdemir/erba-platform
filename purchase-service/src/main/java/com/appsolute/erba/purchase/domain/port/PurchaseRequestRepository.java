package com.appsolute.erba.purchase.domain.port;

import com.appsolute.erba.purchase.domain.model.PurchaseRequest;
import com.appsolute.erba.purchase.domain.valueobject.PurchaseRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseRequestRepository {

    PurchaseRequest save(PurchaseRequest purchaseRequest);

    Optional<PurchaseRequest> findById(UUID id);

    Page<PurchaseRequest> findAll(Pageable pageable);

    Page<PurchaseRequest> findByRequesterUserId(UUID requesterUserId, Pageable pageable);

    Page<PurchaseRequest> findByStatus(PurchaseRequestStatus status, Pageable pageable);

    Page<PurchaseRequest> findByRequesterUserIdAndStatus(
            UUID requesterUserId,
            PurchaseRequestStatus status,
            Pageable pageable
    );
}