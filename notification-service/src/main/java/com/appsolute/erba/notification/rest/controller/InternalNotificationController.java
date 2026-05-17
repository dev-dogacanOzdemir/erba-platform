package com.appsolute.erba.notification.rest.controller;

import com.appsolute.erba.notification.application.dto.CreateTargetedNotificationCommand;
import com.appsolute.erba.notification.application.dto.CreateTargetedNotificationResult;
import com.appsolute.erba.notification.application.service.CreateTargetedNotificationService;
import com.appsolute.erba.notification.rest.dto.InternalCreateTargetedNotificationRequest;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/notifications")
public class InternalNotificationController {

    private final CreateTargetedNotificationService
            createTargetedNotificationService;

    public InternalNotificationController(
            CreateTargetedNotificationService createTargetedNotificationService
    ) {
        this.createTargetedNotificationService =
                createTargetedNotificationService;
    }

    @PostMapping("/targeted")
    public ApiResponse<CreateTargetedNotificationResult> create(
            @Valid
            @RequestBody
            InternalCreateTargetedNotificationRequest request
    ) {

        CreateTargetedNotificationResult result =
                createTargetedNotificationService.create(
                        new CreateTargetedNotificationCommand(
                                request.targetType(),
                                request.targetKey(),
                                request.title(),
                                request.message(),
                                request.type(),
                                request.sourceService(),
                                request.sourceResourceType(),
                                request.sourceResourceId()
                        )
                );

        return ApiResponse.success(result);
    }
}