package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository {

    AuthUser save(AuthUser authUser);

    Optional<AuthUser> findById(UUID id);

    Optional<AuthUser> findByEmail(String email);

    boolean existsByEmail(String email);

    List<AuthUser> findAll();

    List<AuthUser> findActiveUsersByRole(AuthRole role);
}