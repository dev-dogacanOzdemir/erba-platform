package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.DeleteIdentityUserCommand;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteIdentityUserService {

    private final IdentityUserRepository identityUserRepository;
    private final AuditEventPublisher auditEventPublisher;

    public DeleteIdentityUserService(
            IdentityUserRepository identityUserRepository,
            AuditEventPublisher auditEventPublisher
    ) {
        this.identityUserRepository = identityUserRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void delete(DeleteIdentityUserCommand command) {
        IdentityUser identityUser = identityUserRepository.findById(command.identityUserId())
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        identityUser.softDelete();

        identityUserRepository.save(identityUser);

        auditEventPublisher.publish(
                command.actorUserId(),
                "IDENTITY_USER_DELETED",
                "IDENTITY_USER",
                identityUser.getId(),
                "Identity user deleted: " + identityUser.getEmail()
        );
    }
}