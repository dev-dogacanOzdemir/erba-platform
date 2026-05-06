package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.RefreshCommand;
import com.appsolute.erba.auth.application.dto.RefreshResult;
import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.exception.InvalidRefreshTokenException;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.model.RefreshToken;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RefreshService {

    private static final long ACCESS_TOKEN_EXPIRES_IN_SECONDS = 900;
    private static final long REFRESH_TOKEN_EXPIRES_IN_DAYS = 7;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserRepository authUserRepository;
    private final TokenGenerator tokenGenerator;

    public RefreshService(
            RefreshTokenRepository refreshTokenRepository,
            AuthUserRepository authUserRepository,
            TokenGenerator tokenGenerator
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authUserRepository = authUserRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional
    public RefreshResult refresh(RefreshCommand command) {

        // 1) raw → hash
        String hash = tokenGenerator.hashToken(command.refreshToken());

        // 2) DB’de bul
        RefreshToken token = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(InvalidRefreshTokenException::new);

        // 3) geçerli mi?
        if (token.isRevoked() || token.isExpired()) {
            throw new InvalidRefreshTokenException();
        }

        // 4) user getir
        AuthUser user = authUserRepository.findById(token.getUserId())
                .orElseThrow(InvalidRefreshTokenException::new);

        // 5) eski token’ı revoke et
        token.revoke();
        refreshTokenRepository.save(token);

        // 6) yeni access
        String newAccessToken = tokenGenerator.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        // 7) yeni refresh
        String rawRefresh = tokenGenerator.generateRefreshToken();
        String newHash = tokenGenerator.hashToken(rawRefresh);

        RefreshToken newToken = RefreshToken.create(
                user.getId(),
                newHash,
                LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRES_IN_DAYS)
        );

        refreshTokenRepository.save(newToken);

        return new RefreshResult(
                newAccessToken,
                rawRefresh,
                ACCESS_TOKEN_EXPIRES_IN_SECONDS
        );
    }
}