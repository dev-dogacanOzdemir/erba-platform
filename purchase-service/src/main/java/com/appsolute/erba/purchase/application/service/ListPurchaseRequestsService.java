package com.appsolute.erba.purchase.application.service;

import com.appsolute.erba.purchase.application.dto.ListPurchaseRequestResult;
import com.appsolute.erba.purchase.domain.model.PurchaseRequest;
import com.appsolute.erba.purchase.domain.port.PurchaseRequestRepository;
import com.appsolute.erba.purchase.domain.valueobject.PurchaseRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListPurchaseRequestsService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    public ListPurchaseRequestsService(
            PurchaseRequestRepository purchaseRequestRepository
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    @Transactional(readOnly = true)
    public Page<ListPurchaseRequestResult> list(
            String status,
            int page,
            int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (status == null || status.isBlank()) {
            return purchaseRequestRepository.findAll(pageRequest)
                    .map(this::toResult);
        }

        PurchaseRequestStatus requestStatus =
                PurchaseRequestStatus.valueOf(status.toUpperCase());

        return purchaseRequestRepository.findByStatus(
                        requestStatus,
                        pageRequest
                )
                .map(this::toResult);
    }

    private ListPurchaseRequestResult toResult(PurchaseRequest purchaseRequest) {
        return new ListPurchaseRequestResult(
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
}