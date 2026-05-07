package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.ListAuthUserResult;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListAuthUsersService {

    private final AuthUserRepository authUserRepository;

    public ListAuthUsersService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional(readOnly = true)
    public List<ListAuthUserResult> list() {
        return authUserRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() != AuthUserStatus.DELETED)
                .map(this::toResult)
                .toList();
    }

    private ListAuthUserResult toResult(AuthUser user) {
        return new ListAuthUserResult(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getRole().getLabel(),
                user.getStatus().name(),
                user.getStatus().getLabel(),
                user.getFailedLoginCount(),
                user.getLockedUntil(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}