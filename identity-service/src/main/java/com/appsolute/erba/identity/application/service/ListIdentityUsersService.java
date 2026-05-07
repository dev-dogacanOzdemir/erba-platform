package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.ListIdentityUserResult;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListIdentityUsersService {

    private final IdentityUserRepository identityUserRepository;

    public ListIdentityUsersService(
            IdentityUserRepository identityUserRepository
    ) {
        this.identityUserRepository = identityUserRepository;
    }

    @Transactional(readOnly = true)
    public List<ListIdentityUserResult> getAll() {

        return identityUserRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .map(this::toResult)
                .toList();
    }

    private ListIdentityUserResult toResult(IdentityUser identityUser) {

        return new ListIdentityUserResult(
                identityUser.getId(),
                identityUser.getAuthUserId(),

                identityUser.getUserType().name(),
                identityUser.getUserType().getLabel(),

                identityUser.getStatus().name(),
                identityUser.getStatus().getLabel(),

                identityUser.getEmail(),
                identityUser.getFirstName(),
                identityUser.getLastName(),
                identityUser.getPhone(),

                identityUser.getProfilePhotoId(),

                identityUser.getCreatedAt(),
                identityUser.getUpdatedAt()
        );
    }
}