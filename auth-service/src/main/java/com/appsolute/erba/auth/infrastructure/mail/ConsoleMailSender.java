package com.appsolute.erba.auth.infrastructure.mail;

import com.appsolute.erba.auth.application.port.MailSender;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMailSender implements MailSender {

    @Override
    public void sendPasswordResetMail(String toEmail, String resetToken) {
        System.out.println("Password reset mail sent to: " + toEmail);
        System.out.println("Password reset token: " + resetToken);
    }
}