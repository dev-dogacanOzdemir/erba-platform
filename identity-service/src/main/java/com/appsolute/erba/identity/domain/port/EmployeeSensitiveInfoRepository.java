package com.appsolute.erba.identity.domain.port;

import com.appsolute.erba.identity.domain.model.EmployeeSensitiveInfo;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeSensitiveInfoRepository {

    EmployeeSensitiveInfo save(EmployeeSensitiveInfo info);

    Optional<EmployeeSensitiveInfo> findByIdentityUserId(UUID identityUserId);
}