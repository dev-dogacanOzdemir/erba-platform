package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.LinkAuthUserCommand;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.shared.exception.ConflictException;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkAuthUserService {

    private final IdentityUserRepository identityUserRepository;

    public LinkAuthUserService(IdentityUserRepository identityUserRepository) {
        this.identityUserRepository = identityUserRepository;
    }

    @Transactional
    public void link(LinkAuthUserCommand command) {

        IdentityUser identityUser = identityUserRepository
                .findById(command.identityUserId())
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (identityUserRepository.existsByAuthUserId(command.authUserId())) {
            throw new ConflictException(ErrorCode.AUTH_USER_ALREADY_LINKED);
        }

        identityUser.linkAuthUser(command.authUserId());

        identityUserRepository.save(identityUser);
    }
}