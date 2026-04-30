package com.appsolute.erba.auth.domain.model;

import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {

    private final UserId id;
    private final Email email;
    private final PasswordHash passwordHash;

    private boolean active;
    private int failedLoginCount;
    private LocalDateTime lockedUntil;

    public User(
            UserId id,
            Email email,
            PasswordHash passwordHash,
            boolean active,
            int failedLoginCount,
            LocalDateTime lockedUntil
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.failedLoginCount = failedLoginCount;
        this.lockedUntil = lockedUntil;
    }

    public boolean isLocked() {
        return lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now());
    }

    public void increaseFailedLoginCount() {
        this.failedLoginCount++;
    }

    public void resetFailedLoginCount() {
        this.failedLoginCount = 0;
        this.lockedUntil = null;
    }

    public void lockUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }
}