package com.appsolute.erba.audit.application.dto;

import java.util.List;

public record PagedAuditLogResult(
        List<ListAuditLogResult> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}