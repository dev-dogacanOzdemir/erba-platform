package com.appsolute.erba.notification.rest.controller;

import com.appsolute.erba.notification.application.dto.CreateNotificationCommand;
import com.appsolute.erba.notification.application.dto.CreateNotificationResult;
import com.appsolute.erba.notification.application.dto.ListNotificationResult;
import com.appsolute.erba.notification.application.dto.UnreadNotificationCountResult;
import com.appsolute.erba.notification.application.service.CreateNotificationService;
import com.appsolute.erba.notification.application.service.GetUnreadNotificationCountService;
import com.appsolute.erba.notification.application.service.ListMyNotificationsService;
import com.appsolute.erba.notification.application.service.MarkAllNotificationsAsReadService;
import com.appsolute.erba.notification.application.service.MarkNotificationAsReadService;
import com.appsolute.erba.notification.rest.dto.CreateNotificationRequest;
import com.appsolute.erba.notification.rest.dto.CreateNotificationResponse;
import com.appsolute.erba.notification.rest.dto.ListNotificationResponse;
import com.appsolute.erba.notification.rest.dto.UnreadNotificationCountResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.response.PageResponse;
import com.appsolute.erba.shared.security.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private static final int MAX_PAGE_SIZE = 100;

    private final CreateNotificationService createNotificationService;
    private final ListMyNotificationsService listMyNotificationsService;
    private final MarkNotificationAsReadService markNotificationAsReadService;
    private final GetUnreadNotificationCountService getUnreadNotificationCountService;
    private final MarkAllNotificationsAsReadService markAllNotificationsAsReadService;

    public NotificationController(
            CreateNotificationService createNotificationService,
            ListMyNotificationsService listMyNotificationsService,
            MarkNotificationAsReadService markNotificationAsReadService,
            GetUnreadNotificationCountService getUnreadNotificationCountService,
            MarkAllNotificationsAsReadService markAllNotificationsAsReadService
    ) {
        this.createNotificationService = createNotificationService;
        this.listMyNotificationsService = listMyNotificationsService;
        this.markNotificationAsReadService = markNotificationAsReadService;
        this.getUnreadNotificationCountService = getUnreadNotificationCountService;
        this.markAllNotificationsAsReadService = markAllNotificationsAsReadService;
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
    public ApiResponse<PageResponse<ListNotificationResponse>> listMine(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        Page<ListNotificationResult> resultPage =
                listMyNotificationsService.list(
                        authenticatedUser.userId(),
                        safePage,
                        safeSize
                );

        PageResponse<ListNotificationResponse> response =
                new PageResponse<>(
                        resultPage.getContent()
                                .stream()
                                .map(this::toResponse)
                                .toList(),
                        resultPage.getNumber(),
                        resultPage.getSize(),
                        resultPage.getTotalElements(),
                        resultPage.getTotalPages(),
                        resultPage.isFirst(),
                        resultPage.isLast()
                );

        return ApiResponse.success(response);
    }

    @GetMapping("/me/unread-count")
    public ApiResponse<UnreadNotificationCountResponse> unreadCount(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        UnreadNotificationCountResult result =
                getUnreadNotificationCountService.get(
                        authenticatedUser.userId()
                );

        return ApiResponse.success(
                new UnreadNotificationCountResponse(result.count())
        );
    }

    @PatchMapping("/{id}/read")
    public ApiResponse<Void> markAsRead(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable("id") UUID id
    ) {
        markNotificationAsReadService.markAsRead(
                authenticatedUser.userId(),
                id
        );

        return ApiResponse.success(null);
    }

    @PatchMapping("/me/read-all")
    public ApiResponse<Void> markAllAsRead(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        markAllNotificationsAsReadService.markAll(
                authenticatedUser.userId()
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