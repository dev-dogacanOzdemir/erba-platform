package com.appsolute.erba.identity.domain.port;

import com.appsolute.erba.identity.domain.model.IdentityUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdentityUserRepository {

    IdentityUser save(IdentityUser user);

    Optional<IdentityUser> findById(UUID id);

    Optional<IdentityUser> findByAuthUserId(UUID authUserId);

    boolean existsByEmail(String email);

    boolean existsByAuthUserId(UUID authUserId);

    List<IdentityUser> findAll();

    
}