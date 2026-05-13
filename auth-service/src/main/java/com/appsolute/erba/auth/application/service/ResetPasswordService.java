package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.ResetPasswordCommand;
import com.appsolute.erba.auth.application.port.PasswordHasher;
import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.exception.InvalidPasswordResetTokenException;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.model.PasswordResetToken;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.port.PasswordResetTokenRepository;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.shared.audit.AuditEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AuthUserRepository authUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenGenerator tokenGenerator;
    private final PasswordHasher passwordHasher;
    private final AuditEventPublisher auditEventPublisher;

    public ResetPasswordService(
            PasswordResetTokenRepository passwordResetTokenRepository,
            AuthUserRepository authUserRepository,
            RefreshTokenRepository refreshTokenRepository,
            TokenGenerator tokenGenerator,
            PasswordHasher passwordHasher,
            AuditEventPublisher auditEventPublisher
    ) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.authUserRepository = authUserRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenGenerator = tokenGenerator;
        this.passwordHasher = passwordHasher;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void resetPassword(ResetPasswordCommand command) {
        String tokenHash = tokenGenerator.hashToken(command.resetToken());

        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(InvalidPasswordResetTokenException::new);

        if (!resetToken.isUsable()) {
            throw new InvalidPasswordResetTokenException();
        }

        AuthUser authUser = authUserRepository.findById(resetToken.getUserId())
                .filter(user -> !user.isDeleted())
                .orElseThrow(InvalidPasswordResetTokenException::new);

        String newPasswordHash = passwordHasher.hash(command.newPassword());

        authUser.changePassword(newPasswordHash);
        authUserRepository.save(authUser);

        resetToken.markAsUsed();
        passwordResetTokenRepository.save(resetToken);

        refreshTokenRepository.revokeAllActiveTokensByUserId(authUser.getId());

        auditEventPublisher.publish(
                authUser.getId(),
                "PASSWORD_RESET_COMPLETED",
                "AUTH_USER",
                authUser.getId(),
                "Password reset completed for user: " + authUser.getEmail()
        );
    }
}