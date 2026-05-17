package com.appsolute.erba.shared.notification;

import com.appsolute.erba.shared.notification.dto.SendTargetedNotificationRequest;

import java.util.UUID;

public interface NotificationClient {

    void send(
            UUID recipientUserId,
            String title,
            String message,
            String type,
            String sourceService,
            String sourceResourceType,
            UUID sourceResourceId
    );

    void sendTargetedNotification(

            SendTargetedNotificationRequest request

    );
}