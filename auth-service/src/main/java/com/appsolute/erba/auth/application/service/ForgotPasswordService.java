package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.ForgotPasswordCommand;
import com.appsolute.erba.auth.application.port.AuditEventPublisher;
import com.appsolute.erba.auth.application.port.MailSender;
import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.model.PasswordResetToken;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.port.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ForgotPasswordService {

    private static final long RESET_TOKEN_EXPIRES_IN_MINUTES = 15;

    private final AuthUserRepository authUserRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final TokenGenerator tokenGenerator;
    private final MailSender mailSender;
    private final AuditEventPublisher auditEventPublisher;

    public ForgotPasswordService(
            AuthUserRepository authUserRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            TokenGenerator tokenGenerator,
            MailSender mailSender,
            AuditEventPublisher auditEventPublisher
    ) {
        this.authUserRepository = authUserRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.tokenGenerator = tokenGenerator;
        this.mailSender = mailSender;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordCommand command) {
        authUserRepository.findByEmail(command.email())
                .filter(user -> !user.isDeleted())
                .ifPresent(this::createAndSendResetToken);
    }

    private void createAndSendResetToken(AuthUser user) {
        passwordResetTokenRepository.revokeAllUsableTokensByUserId(user.getId());

        String rawToken = tokenGenerator.generatePasswordResetToken();
        String tokenHash = tokenGenerator.hashToken(rawToken);

        PasswordResetToken resetToken = PasswordResetToken.create(
                user.getId(),
                tokenHash,
                LocalDateTime.now().plusMinutes(RESET_TOKEN_EXPIRES_IN_MINUTES)
        );

        passwordResetTokenRepository.save(resetToken);

        mailSender.sendPasswordResetMail(user.getEmail(), rawToken);

        auditEventPublisher.publish(
                user.getId(),
                "PASSWORD_RESET_REQUESTED",
                "AUTH_USER",
                user.getId(),
                "Password reset requested for user: " + user.getEmail()
        );
    }
}