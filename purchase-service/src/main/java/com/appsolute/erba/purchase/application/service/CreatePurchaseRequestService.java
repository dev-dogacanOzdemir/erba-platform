package com.appsolute.erba.purchase.application.service;

import com.appsolute.erba.purchase.application.dto.CreatePurchaseRequestCommand;
import com.appsolute.erba.purchase.application.dto.CreatePurchaseRequestResult;
import com.appsolute.erba.purchase.domain.model.PurchaseRequest;
import com.appsolute.erba.purchase.domain.port.PurchaseRequestRepository;
import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.notification.NotificationClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final AuditEventPublisher auditEventPublisher;
    private final NotificationClient notificationClient;

    public CreatePurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            AuditEventPublisher auditEventPublisher,
            NotificationClient notificationClient
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.auditEventPublisher = auditEventPublisher;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public CreatePurchaseRequestResult create(CreatePurchaseRequestCommand command) {
        PurchaseRequest purchaseRequest = PurchaseRequest.create(
                command.requesterUserId(),
                command.productName(),
                command.quantity(),
                command.description()
        );

        PurchaseRequest savedPurchaseRequest =
                purchaseRequestRepository.save(purchaseRequest);

        auditEventPublisher.publish(
                command.requesterUserId(),
                "PURCHASE_REQUEST_CREATED",
                "PURCHASE_REQUEST",
                savedPurchaseRequest.getId(),
                "Purchase request created: " + savedPurchaseRequest.getProductName()
        );

        notificationClient.send(
                command.requesterUserId(),
                "Satınalma talebiniz oluşturuldu",
                "Talebiniz beklemeye alındı: " + savedPurchaseRequest.getProductName(),
                "PURCHASE_REQUEST_CREATED",
                "purchase-service",
                "PURCHASE_REQUEST",
                savedPurchaseRequest.getId()
        );

        return new CreatePurchaseRequestResult(savedPurchaseRequest.getId());
    }
}