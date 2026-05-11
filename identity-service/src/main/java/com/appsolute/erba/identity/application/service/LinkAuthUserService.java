package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.LinkAuthUserCommand;
import com.appsolute.erba.identity.application.port.AuditEventPublisher;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkAuthUserService {

    private final IdentityUserRepository identityUserRepository;
    private final AuditEventPublisher auditEventPublisher;

    public LinkAuthUserService(
            IdentityUserRepository identityUserRepository,
            AuditEventPublisher auditEventPublisher
    ) {
        this.identityUserRepository = identityUserRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void link(LinkAuthUserCommand command) {

        IdentityUser identityUser = identityUserRepository
                .findById(command.identityUserId())
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND)
                );

        identityUser.linkAuthUser(command.authUserId());

        identityUserRepository.save(identityUser);

        auditEventPublisher.publish(
                command.actorUserId(),
                "IDENTITY_AUTH_LINKED",
                "IDENTITY_USER",
                identityUser.getId(),
                "Identity user linked with auth user: "
                        + command.authUserId()
        );
    }
}