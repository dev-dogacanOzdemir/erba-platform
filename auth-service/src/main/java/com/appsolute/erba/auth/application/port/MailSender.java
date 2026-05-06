package com.appsolute.erba.auth.application.port;

public interface MailSender {

    void sendPasswordResetMail(String toEmail, String resetToken);
}