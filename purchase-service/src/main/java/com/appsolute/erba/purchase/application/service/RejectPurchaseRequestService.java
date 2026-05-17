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
public class RejectPurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final AuditEventPublisher auditEventPublisher;
    private final NotificationClient notificationClient;

    public RejectPurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            AuditEventPublisher auditEventPublisher,
            NotificationClient notificationClient
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.auditEventPublisher = auditEventPublisher;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public void reject(ReviewPurchaseRequestCommand command) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(
                        command.purchaseRequestId()
                )
                .orElseThrow(() -> new NotFoundException(ErrorCode.PURCHASE_REQUEST_NOT_FOUND));

        purchaseRequest.reject(
                command.reviewerUserId(),
                command.reviewNote()
        );

        purchaseRequestRepository.save(purchaseRequest);

        auditEventPublisher.publish(
                command.reviewerUserId(),
                "PURCHASE_REQUEST_REJECTED",
                "PURCHASE_REQUEST",
                purchaseRequest.getId(),
                "Purchase request rejected: " + purchaseRequest.getProductName()
        );

        notificationClient.send(
                purchaseRequest.getRequesterUserId(),
                "Satınalma talebiniz reddedildi",
                "Talebiniz reddedildi: " + purchaseRequest.getProductName(),
                "PURCHASE_REQUEST_REJECTED",
                "purchase-service",
                "PURCHASE_REQUEST",
                purchaseRequest.getId()
        );
    }
}