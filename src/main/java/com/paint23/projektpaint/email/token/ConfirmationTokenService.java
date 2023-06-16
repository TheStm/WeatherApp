package com.paint23.projektpaint.email.token;

import com.paint23.projektpaint.RepositoryJsonReader;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConfirmationTokenService {
    private final static String TOKEN_NOT_FOUND_MESSAGE = "token not found";
    private final static String EMAIL_ALREADY_CONFIRMED_MESSAGE = "email already confirmed";
    private final static String TOKEN_EXPIRED_MESSAGE = "token expired";
    private QuickStart quickStart;
    private RepositoryJsonReader repositoryJsonReader;

    public ConfirmationTokenService(QuickStart quickStart){
        this.quickStart = quickStart;
    }
    public void saveConfirmationToken(User user, ConfirmationToken token) {
        System.out.println(token.getToken());
        quickStart.appendToken(user, token);
    }

    public void setConfirmedAt(String token) {
        User user = repositoryJsonReader.getTokenFromRepository(token).getUser();
        quickStart.updateConfirmedAt(user, token, LocalDateTime.now());
    }

    public Boolean confirmToken(String token) {
        ConfirmationToken confirmationToken = repositoryJsonReader.getTokenFromRepository(token);
        if(confirmationToken == null)
            throw new IllegalStateException(TOKEN_NOT_FOUND_MESSAGE);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException(EMAIL_ALREADY_CONFIRMED_MESSAGE);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(TOKEN_EXPIRED_MESSAGE);
        }

        setConfirmedAt(token);
        quickStart.enableUser(confirmationToken.getUser().getUsername());

        return true;
    }
}
