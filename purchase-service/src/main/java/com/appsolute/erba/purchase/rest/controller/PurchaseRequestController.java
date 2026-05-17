package com.appsolute.erba.purchase.rest.controller;

import com.appsolute.erba.purchase.application.dto.CreatePurchaseRequestCommand;
import com.appsolute.erba.purchase.application.dto.CreatePurchaseRequestResult;
import com.appsolute.erba.purchase.application.dto.ListPurchaseRequestResult;
import com.appsolute.erba.purchase.application.dto.ReviewPurchaseRequestCommand;
import com.appsolute.erba.purchase.application.service.*;
import com.appsolute.erba.purchase.rest.dto.CreatePurchaseRequestRequest;
import com.appsolute.erba.purchase.rest.dto.CreatePurchaseRequestResponse;
import com.appsolute.erba.purchase.rest.dto.ListPurchaseRequestResponse;
import com.appsolute.erba.purchase.rest.dto.ReviewPurchaseRequestRequest;
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
@RequestMapping("/api/v1/purchase-requests")
public class PurchaseRequestController {

    private static final int MAX_PAGE_SIZE = 100;

    private final CreatePurchaseRequestService createPurchaseRequestService;
    private final ListPurchaseRequestsService listPurchaseRequestsService;
    private final ApprovePurchaseRequestService approvePurchaseRequestService;
    private final RejectPurchaseRequestService rejectPurchaseRequestService;
    private final ListMyPurchaseRequestsService listMyPurchaseRequestsService;

    public PurchaseRequestController(
            CreatePurchaseRequestService createPurchaseRequestService,
            ListPurchaseRequestsService listPurchaseRequestsService,
            ApprovePurchaseRequestService approvePurchaseRequestService,
            RejectPurchaseRequestService rejectPurchaseRequestService,
            ListMyPurchaseRequestsService listMyPurchaseRequestsService
    ) {
        this.createPurchaseRequestService = createPurchaseRequestService;
        this.listPurchaseRequestsService = listPurchaseRequestsService;
        this.approvePurchaseRequestService = approvePurchaseRequestService;
        this.rejectPurchaseRequestService = rejectPurchaseRequestService;
        this.listMyPurchaseRequestsService = listMyPurchaseRequestsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreatePurchaseRequestResponse> create(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CreatePurchaseRequestRequest request
    ) {
        CreatePurchaseRequestResult result = createPurchaseRequestService.create(
                new CreatePurchaseRequestCommand(
                        authenticatedUser.userId(),
                        request.productName(),
                        request.quantity(),
                        request.description()
                )
        );

        return ApiResponse.success(
                new CreatePurchaseRequestResponse(result.purchaseRequestId())
        );
    }

    @GetMapping
    public ApiResponse<PageResponse<ListPurchaseRequestResponse>> list(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        Page<ListPurchaseRequestResult> resultPage =
                listPurchaseRequestsService.list(status, safePage, safeSize);

        return ApiResponse.success(
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
                )
        );
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<Void> approve(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable("id") UUID id,
            @Valid @RequestBody ReviewPurchaseRequestRequest request
    ) {
        approvePurchaseRequestService.approve(
                new ReviewPurchaseRequestCommand(
                        authenticatedUser.userId(),
                        id,
                        request.reviewNote()
                )
        );

        return ApiResponse.success(null);
    }

    @PatchMapping("/{id}/reject")
    public ApiResponse<Void> reject(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable("id") UUID id,
            @Valid @RequestBody ReviewPurchaseRequestRequest request
    ) {
        rejectPurchaseRequestService.reject(
                new ReviewPurchaseRequestCommand(
                        authenticatedUser.userId(),
                        id,
                        request.reviewNote()
                )
        );

        return ApiResponse.success(null);
    }

    @GetMapping("/me")
    public ApiResponse<PageResponse<ListPurchaseRequestResponse>> listMine(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        Page<ListPurchaseRequestResult> resultPage =
                listMyPurchaseRequestsService.list(
                        authenticatedUser.userId(),
                        status,
                        safePage,
                        safeSize
                );

        return ApiResponse.success(
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
                )
        );
    }

    private ListPurchaseRequestResponse toResponse(ListPurchaseRequestResult result) {
        return new ListPurchaseRequestResponse(
                result.id(),
                result.requesterUserId(),
                result.productName(),
                result.quantity(),
                result.description(),
                result.status(),
                result.reviewedByUserId(),
                result.reviewedAt(),
                result.reviewNote(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}