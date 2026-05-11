package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.ChangeUserStatusCommand;
import com.appsolute.erba.auth.application.port.AuditEventPublisher;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserStatusService {

    private final AuthUserRepository authUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuditEventPublisher auditEventPublisher;

    public ChangeUserStatusService(
            AuthUserRepository authUserRepository,
            RefreshTokenRepository refreshTokenRepository, AuditEventPublisher auditEventPublisher
            ) {
        this.authUserRepository = authUserRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void changeStatus(ChangeUserStatusCommand command) {
        AuthUser authUser = authUserRepository.findById(command.userId())
                .filter(user -> user.getStatus() != AuthUserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        authUser.changeStatus(command.status());

        auditEventPublisher.publish(
                command.actorUserId(),
                "AUTH_USER_STATUS_CHANGED",
                "AUTH_USER",
                authUser.getId(),
                "User status changed for user "
                        + authUser.getEmail()
                        + " to "
                        + authUser.getStatus().name()
        );

        authUserRepository.save(authUser);

        if (command.status() != AuthUserStatus.ACTIVE) {
            refreshTokenRepository.revokeAllActiveTokensByUserId(authUser.getId());
        }
    }
}