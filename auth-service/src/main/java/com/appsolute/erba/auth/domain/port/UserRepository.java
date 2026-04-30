package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.UserId;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(Email email);

    Optional<User> findById(UserId userId);

    User save(User user);
}