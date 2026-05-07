package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.ChangeUserRoleCommand;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserRoleService {

    private final AuthUserRepository authUserRepository;

    public ChangeUserRoleService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional
    public void changeRole(ChangeUserRoleCommand command) {
        AuthUser authUser = authUserRepository.findById(command.userId())
                .filter(user -> user.getStatus() != AuthUserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        authUser.changeRole(command.role());

        authUserRepository.save(authUser);
    }
}