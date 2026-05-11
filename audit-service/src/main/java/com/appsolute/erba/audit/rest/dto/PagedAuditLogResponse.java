package com.appsolute.erba.audit.rest.dto;

import java.util.List;

public record PagedAuditLogResponse(
        List<ListAuditLogResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}