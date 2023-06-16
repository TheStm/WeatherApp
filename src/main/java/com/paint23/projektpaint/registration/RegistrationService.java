package com.paint23.projektpaint.registration;

import com.paint23.projektpaint.email.EmailService;
import com.paint23.projektpaint.email.EmailWriter;
import com.paint23.projektpaint.email.token.TokenCreator;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import com.paint23.projektpaint.user.UserRole;
import com.paint23.projektpaint.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * This class is a service for registration of new users
 */
@Service
@Getter
public class RegistrationService {

    private  UserService userService;
    private EmailWriter emailWriter;
    private EmailService emailService;

    public RegistrationService() {
    }

    public RegistrationService(QuickStart quickStart) {
        this.userService = new UserService(quickStart);
        this.emailWriter = new EmailWriter();
        this.emailService = new EmailService();
    }

    /**
     * This method creates new user object from request.
     * @param request
     * @return
     */
    public Boolean register(RegistrationRequest request) {
        User user = new User(
                request.name(),
                request.userName(),
                request.email(),
                request.password(),
                UserRole.USER);
        userService.signUpUser(user);
        return true;
    }
}

