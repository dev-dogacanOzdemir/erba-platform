package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.CreateAuthUserByAdminCommand;
import com.appsolute.erba.auth.application.dto.CreateAuthUserByAdminResult;
import com.appsolute.erba.auth.application.port.AuditEventPublisher;
import com.appsolute.erba.auth.application.port.PasswordHasher;
import com.appsolute.erba.auth.domain.exception.AuthUserAlreadyExistsException;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAuthUserByAdminService {

    private final AuthUserRepository authUserRepository;
    private final PasswordHasher passwordHasher;
    private final AuditEventPublisher auditEventPublisher;

    public CreateAuthUserByAdminService(
            AuthUserRepository authUserRepository,
            PasswordHasher passwordHasher,
            AuditEventPublisher auditEventPublisher
    ) {
        this.authUserRepository = authUserRepository;
        this.passwordHasher = passwordHasher;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public CreateAuthUserByAdminResult create(CreateAuthUserByAdminCommand command) {
        if (authUserRepository.existsByEmail(command.email())) {
            throw new AuthUserAlreadyExistsException(command.email());
        }

        String passwordHash = passwordHasher.hash(command.temporaryPassword());

        AuthUser authUser = AuthUser.create(
                command.email(),
                passwordHash,
                command.role()
        );

        authUser.changeStatus(command.status());

        AuthUser savedUser = authUserRepository.save(authUser);

        auditEventPublisher.publish(
                command.actorUserId(),
                "AUTH_USER_CREATED",
                "AUTH_USER",
                savedUser.getId(),
                "Admin created auth user: " + savedUser.getEmail()
        );

        return new CreateAuthUserByAdminResult(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getRole().getLabel(),
                savedUser.getStatus().name(),
                savedUser.getStatus().getLabel()
        );
    }
}