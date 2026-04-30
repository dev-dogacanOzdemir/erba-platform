package com.appsolute.erba.identity.domain.port;

import com.appsolute.erba.identity.domain.model.EmployeeProfile;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeProfileRepository {

    EmployeeProfile save(EmployeeProfile profile);

    Optional<EmployeeProfile> findByIdentityUserId(UUID identityUserId);
}