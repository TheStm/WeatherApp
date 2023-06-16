package com.paint23.projektpaint.user;

import com.paint23.projektpaint.RepositoryJsonReader;
import com.paint23.projektpaint.model.QuickStart;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class enables to handle app users.
 */

@Service
@Getter
public class UserService {
    private final static String USER_NOT_FOUND_MESSAGE = "User with username: %s not found";
    private final static String EMAIL_ALREADY_TAKEN_MESSAGE = "Email already taken";
    private final static String USERNAME_ALREADY_TAKEN_MESSAGE = "Username already taken";
    private QuickStart quickStart;
    private RepositoryJsonReader repositoryJsonReader;

    public UserService() {
    }

    public UserService(QuickStart quickStart) {
        this.quickStart = quickStart;
        this.repositoryJsonReader = new RepositoryJsonReader(quickStart);
    }

    public void setQuickStart(QuickStart quickStart) {
        this.quickStart = quickStart;
    }

    private Boolean findByUserName(String userName){
        String stringFromRepo = quickStart.getClientRecordByLogin(userName);
        if(stringFromRepo == null)
            return false;
        return true;
    }

    private Boolean findByEmail(String email){
        String stringFromRepo = quickStart.getClientRecordByEmail(email);
        if(stringFromRepo == null)
            return false;
        return true;
    }

    /**
     * This method loads user from repository.
     * @param  userName
     * @return User object User
     * @throws UsernameNotFoundException
     */
    public User loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = repositoryJsonReader.getUserFromRepository(userName);
        if(user == null) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userName));
        }
    return user;
    }

    /**
     *This method checks if username or email is taken and if not saves User data in DB.
     * @param user object User
     * @return  true if username and email are not taken
     */
    public Boolean signUpUser(User user) {
        boolean userNameTaken = findByUserName(user.getUsername());
        boolean emailTaken = findByEmail(user.getEmail());

        if (userNameTaken) {
            throw new IllegalStateException(USERNAME_ALREADY_TAKEN_MESSAGE);
        } else if (emailTaken) {
            throw new IllegalStateException(EMAIL_ALREADY_TAKEN_MESSAGE);
        }

        quickStart.insertUserRecord(user);

        return true;
    }

    /**
     *This method sets user to be enabled to use website.
     * @param username
     */

    public void enableUser(String username) {
        quickStart.enableUser(username);
    }
}
