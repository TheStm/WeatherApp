package com.paint23.projektpaint.login;

import com.paint23.projektpaint.email.EmailValidator;
import com.paint23.projektpaint.exceptions.IncorrectPasswordException;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import com.paint23.projektpaint.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
@AllArgsConstructor
public class LoginService {
    private final static String INCORRECT_PASSWORD_MESSAGE = "this password is incorrect";
    private UserService userService;
    private EmailValidator emailValidator;
    private User currentUser;

    public LoginService(QuickStart quickStart) {
        this.userService = new UserService(quickStart);
        this.emailValidator = new EmailValidator(quickStart);
        this.currentUser = new User();
    }

    public LoginService() {
    }

    protected Boolean passwordValidation(User user, String password){
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    public Boolean login(LoginRequest request) throws IncorrectPasswordException {
        String currentUserName = request.username();
        currentUser = userService.loadUserByUsername(currentUserName);
        if(!passwordValidation(currentUser, request.password())){
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_MESSAGE);
        }
        //if(!emailValidator.test(currentUser)){
         //   return false;
        //}

        return true;
    }
}
