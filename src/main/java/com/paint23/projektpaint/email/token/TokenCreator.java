package com.paint23.projektpaint.email.token;

import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class TokenCreator {
    private ConfirmationTokenService confirmationTokenService;

    public TokenCreator(QuickStart quickStart) {
        this.confirmationTokenService = new ConfirmationTokenService(quickStart);
    }

    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        System.out.println(token);
        confirmationTokenService.saveConfirmationToken(user, confirmationToken);
        return token;
    }

}
