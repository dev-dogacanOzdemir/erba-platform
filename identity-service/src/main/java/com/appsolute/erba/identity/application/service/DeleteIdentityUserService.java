package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteIdentityUserService {

    private final IdentityUserRepository identityUserRepository;

    public DeleteIdentityUserService(IdentityUserRepository identityUserRepository) {
        this.identityUserRepository = identityUserRepository;
    }

    @Transactional
    public void delete(UUID id) {
        IdentityUser identityUser = identityUserRepository.findById(id)
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        identityUser.softDelete();
        identityUserRepository.save(identityUser);
    }
}