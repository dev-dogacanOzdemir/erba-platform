package com.appsolute.erba.notification.rest.controller;

import com.appsolute.erba.notification.application.dto.CreateNotificationCommand;
import com.appsolute.erba.notification.application.dto.CreateNotificationResult;
import com.appsolute.erba.notification.application.dto.ListNotificationResult;
import com.appsolute.erba.notification.application.service.CreateNotificationService;
import com.appsolute.erba.notification.application.service.ListMyNotificationsService;
import com.appsolute.erba.notification.application.service.MarkNotificationAsReadService;
import com.appsolute.erba.notification.rest.dto.CreateNotificationRequest;
import com.appsolute.erba.notification.rest.dto.CreateNotificationResponse;
import com.appsolute.erba.notification.rest.dto.ListNotificationResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.security.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final CreateNotificationService createNotificationService;
    private final ListMyNotificationsService listMyNotificationsService;
    private final MarkNotificationAsReadService markNotificationAsReadService;

    public NotificationController(
            CreateNotificationService createNotificationService,
            ListMyNotificationsService listMyNotificationsService,
            MarkNotificationAsReadService markNotificationAsReadService
    ) {
        this.createNotificationService = createNotificationService;
        this.listMyNotificationsService = listMyNotificationsService;
        this.markNotificationAsReadService = markNotificationAsReadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateNotificationResponse> create(
            @Valid @RequestBody CreateNotificationRequest request
    ) {
        CreateNotificationResult result = createNotificationService.create(
                new CreateNotificationCommand(
                        request.recipientUserId(),
                        request.title(),
                        request.message(),
                        request.type(),
                        request.sourceService(),
                        request.sourceResourceType(),
                        request.sourceResourceId()
                )
        );

        return ApiResponse.success(
                new CreateNotificationResponse(result.notificationId())
        );
    }

    @GetMapping("/me")
    public ApiResponse<List<ListNotificationResponse>> listMine(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        List<ListNotificationResponse> response =
                listMyNotificationsService.list(authenticatedUser.userId())
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ApiResponse.success(response);
    }

    @PatchMapping("/{id}/read")
    public ApiResponse<Void> markAsRead(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable("id") java.util.UUID id
    ) {
        markNotificationAsReadService.markAsRead(
                authenticatedUser.userId(),
                id
        );

        return ApiResponse.success(null);
    }

    private ListNotificationResponse toResponse(ListNotificationResult result) {
        return new ListNotificationResponse(
                result.id(),
                result.recipientUserId(),
                result.title(),
                result.message(),
                result.type(),
                result.sourceService(),
                result.sourceResourceType(),
                result.sourceResourceId(),
                result.read(),
                result.createdAt(),
                result.readAt()
        );
    }
}