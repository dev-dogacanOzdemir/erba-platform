package com.appsolute.erba.auth.infrastructure.persistence;

import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.UserRepository;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(SpringDataUserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return userJpaRepository.findByEmailAndDeletedAtIsNull(email.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue())
                .filter(entity -> entity.getDeletedAt() == null)
                .map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = userJpaRepository.findById(user.getId().getValue())
                .orElseGet(UserJpaEntity::new);

        entity.setId(user.getId().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setPasswordHash(user.getPasswordHash().getValue());
        entity.setActive(user.isActive());
        entity.setFailedLoginCount(user.getFailedLoginCount());
        entity.setLockedUntil(user.getLockedUntil());

        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setUpdatedAt(LocalDateTime.now());

        UserJpaEntity savedEntity = userJpaRepository.save(entity);

        return toDomain(savedEntity);
    }

    private User toDomain(UserJpaEntity entity) {
        return new User(
                UserId.of(entity.getId()),
                Email.of(entity.getEmail()),
                PasswordHash.of(entity.getPasswordHash()),
                entity.isActive(),
                entity.getFailedLoginCount(),
                entity.getLockedUntil()
        );
    }
}