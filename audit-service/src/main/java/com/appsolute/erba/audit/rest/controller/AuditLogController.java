package com.appsolute.erba.audit.rest.controller;

import com.appsolute.erba.audit.application.dto.CreateAuditLogCommand;
import com.appsolute.erba.audit.application.dto.ListAuditLogResult;
import com.appsolute.erba.audit.application.dto.PagedAuditLogResult;
import com.appsolute.erba.audit.application.dto.SearchAuditLogsCommand;
import com.appsolute.erba.audit.application.service.CreateAuditLogService;
import com.appsolute.erba.audit.application.service.ListAuditLogsService;
import com.appsolute.erba.audit.rest.dto.CreateAuditLogRequest;
import com.appsolute.erba.audit.rest.dto.ListAuditLogResponse;
import com.appsolute.erba.audit.rest.dto.PagedAuditLogResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    private final CreateAuditLogService createAuditLogService;
    private final ListAuditLogsService listAuditLogsService;

    public AuditLogController(
            CreateAuditLogService createAuditLogService,
            ListAuditLogsService listAuditLogsService
    ) {
        this.createAuditLogService = createAuditLogService;
        this.listAuditLogsService = listAuditLogsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> create(
            @Valid @RequestBody CreateAuditLogRequest request
    ) {
        createAuditLogService.create(
                new CreateAuditLogCommand(
                        request.sourceService(),
                        request.actorUserId(),
                        request.action(),
                        request.resourceType(),
                        request.resourceId(),
                        request.description()
                )
        );

        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<PagedAuditLogResponse> search(
            @RequestParam(name = "sourceService", required = false) String sourceService,
            @RequestParam(name = "actorUserId", required = false) UUID actorUserId,
            @RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "resourceType", required = false) String resourceType,
            @RequestParam(name = "resourceId", required = false) UUID resourceId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        PagedAuditLogResult result = listAuditLogsService.search(
                new SearchAuditLogsCommand(
                        sourceService,
                        actorUserId,
                        action,
                        resourceType,
                        resourceId,
                        page,
                        size
                )
        );

        return ApiResponse.success(
                new PagedAuditLogResponse(
                        result.content()
                                .stream()
                                .map(this::toResponse)
                                .toList(),
                        result.page(),
                        result.size(),
                        result.totalElements(),
                        result.totalPages(),
                        result.first(),
                        result.last()
                )
        );
    }

    private ListAuditLogResponse toResponse(ListAuditLogResult result) {
        return new ListAuditLogResponse(
                result.id(),
                result.sourceService(),
                result.actorUserId(),
                result.action(),
                result.resourceType(),
                result.resourceId(),
                result.description(),
                result.createdAt()
        );
    }
}