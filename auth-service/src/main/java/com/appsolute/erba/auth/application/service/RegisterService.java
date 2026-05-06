package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.RegisterCommand;
import com.appsolute.erba.auth.application.dto.RegisterResult;
import com.appsolute.erba.auth.domain.exception.AuthUserAlreadyExistsException;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.application.port.PasswordHasher;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final AuthUserRepository authUserRepository;
    private final PasswordHasher passwordHasher;

    public RegisterService(
            AuthUserRepository authUserRepository,
            PasswordHasher passwordHasher
    ) {
        this.authUserRepository = authUserRepository;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public RegisterResult register(RegisterCommand command) {

        // 1. email zaten var mı?
        if (authUserRepository.existsByEmail(command.email())) {
            throw new AuthUserAlreadyExistsException(command.email());
        }

        // 2. password hashle
        String passwordHash = passwordHasher.hash(command.password());

        // 3. domain object oluştur
        AuthUser authUser = AuthUser.create(
                command.email(),
                passwordHash,
                AuthRole.EMPLOYEE
        );

        // 4. persist et
        authUserRepository.save(authUser);

        // 5. result dön
        return new RegisterResult(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getRole(),
                authUser.getStatus()
        );
    }
}