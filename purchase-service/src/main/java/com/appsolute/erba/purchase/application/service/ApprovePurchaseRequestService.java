package com.appsolute.erba.purchase.application.service;

import com.appsolute.erba.purchase.application.dto.ReviewPurchaseRequestCommand;
import com.appsolute.erba.purchase.domain.model.PurchaseRequest;
import com.appsolute.erba.purchase.domain.port.PurchaseRequestRepository;
import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import com.appsolute.erba.shared.notification.NotificationClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovePurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final AuditEventPublisher auditEventPublisher;
    private final NotificationClient notificationClient;

    public ApprovePurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            AuditEventPublisher auditEventPublisher,
            NotificationClient notificationClient
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.auditEventPublisher = auditEventPublisher;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public void approve(ReviewPurchaseRequestCommand command) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(
                        command.purchaseRequestId()
                )
                .orElseThrow(() -> new NotFoundException(ErrorCode.PURCHASE_REQUEST_NOT_FOUND));

        purchaseRequest.approve(
                command.reviewerUserId(),
                command.reviewNote()
        );

        purchaseRequestRepository.save(purchaseRequest);

        auditEventPublisher.publish(
                command.reviewerUserId(),
                "PURCHASE_REQUEST_APPROVED",
                "PURCHASE_REQUEST",
                purchaseRequest.getId(),
                "Purchase request approved: " + purchaseRequest.getProductName()
        );

        notificationClient.send(
                purchaseRequest.getRequesterUserId(),
                "Satınalma talebiniz onaylandı",
                "Talebiniz onaylandı: " + purchaseRequest.getProductName(),
                "PURCHASE_REQUEST_APPROVED",
                "purchase-service",
                "PURCHASE_REQUEST",
                purchaseRequest.getId()
        );
    }
}