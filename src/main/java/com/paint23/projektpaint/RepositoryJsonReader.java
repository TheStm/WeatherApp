package com.paint23.projektpaint;
import com.paint23.projektpaint.email.token.ConfirmationToken;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.user.User;
import com.paint23.projektpaint.user.UserRole;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class RepositoryJsonReader {
    private QuickStart quickStart;
    private String jsonFromRepository;

    public RepositoryJsonReader(QuickStart quickStart) {
        this.quickStart = quickStart;
        this.jsonFromRepository = null;
    }

    public User getUserFromRepository(String userName) {
        jsonFromRepository = quickStart.getClientRecordByLogin(userName);
        if(jsonFromRepository != null) {
            JSONObject json = new JSONObject(jsonFromRepository);
            String name = json.getString("name");
            String username = json.getString("username");
            String email = json.getString("email");
            String password = json.getString("password");
            UserRole userRole = UserRole.valueOf(json.getString("userRole"));

            User user = new User(name, username, email, password, userRole);
            return user;
        }
        return null;
    }

    public ConfirmationToken getTokenFromRepository(String currentToken){
        jsonFromRepository= quickStart.findByToken(currentToken);

        if(jsonFromRepository != null)
        {
            JSONObject json = new JSONObject(jsonFromRepository);
            String name = json.getString("name");
            String username = json.getString("username");
            String email = json.getString("email");
            String password = json.getString("password");
            UserRole userRole = UserRole.valueOf(json.getString("userRole"));

            User user = new User(name, username, email, password, userRole);
            ConfirmationToken confirmationToken =  new ConfirmationToken();

            JSONArray arr = json.getJSONArray("tokens");
            for (int i = 0; i < arr.length(); i++)
            {
                confirmationToken.setToken(arr.getJSONObject(i).getString("token"));
                confirmationToken.setCreatedAt(LocalDateTime.parse(arr.getJSONObject(i).getString("createdAt")));
                confirmationToken.setExpiresAt(LocalDateTime.parse(arr.getJSONObject(i).getString("createdAt")));
                confirmationToken.setExpiresAt(LocalDateTime.parse(arr.getJSONObject(i).getString("confirmedAt")));
            }
            return confirmationToken;
        }

        return null;
    }
}
