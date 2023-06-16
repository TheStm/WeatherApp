package com.paint23.projektpaint.email;

import com.paint23.projektpaint.email.token.TokenCreator;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailValidator  {
    private EmailService emailService = new EmailService();
    private EmailWriter emailWriter = new EmailWriter();
    private QuickStart quickStart;
    private TokenCreator tokenCreator;

    public EmailValidator(QuickStart quickStart) {
        this.quickStart = quickStart;
        this.tokenCreator = new TokenCreator(quickStart);
    }


    public boolean test(User user) {
        if(!user.isEnabled()) {
            String token = tokenCreator.createToken(user);
            String link = "https://localhost:8080/confirm?token=" + token;
            emailService.send(
                    user.getEmail(),
                    emailWriter.buildEmail(
                            user.getEmail(),
                            emailWriter.buildEmail(user.getUsername(), link)
                    )
            );
            return false;
        }
        return true;
    }
}