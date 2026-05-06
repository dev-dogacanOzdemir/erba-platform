package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.LogoutCommand;
import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.model.RefreshToken;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenGenerator tokenGenerator;

    public LogoutService(
            RefreshTokenRepository refreshTokenRepository,
            TokenGenerator tokenGenerator
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional
    public void logout(LogoutCommand command) {
        String tokenHash = tokenGenerator.hashToken(command.refreshToken());

        refreshTokenRepository.findByTokenHash(tokenHash)
                .filter(RefreshToken::isUsable)
                .ifPresent(token -> {
                    token.revoke();
                    refreshTokenRepository.save(token);
                });
    }
}