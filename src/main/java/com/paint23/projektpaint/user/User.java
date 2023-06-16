package com.paint23.projektpaint.user;

import com.paint23.projektpaint.email.token.ConfirmationToken;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 *This class represents app user
 */

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User implements UserDetails {

    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1)
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence"
    )
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    @DBRef
    private List<ConfirmationToken> tokenList;


    /**
     * Class constructor without id, locked and enable. When creating new user id is generated automatically
     * and locked and enabled are set to false. User is enabled only after confirming e-mail.
     * @param name
     * @param username
     * @param email
     * @param password
     * @param userRole
     */
    public User(String name, String username, String email, String password, UserRole userRole) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.tokenList = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * For now account cannot be expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    /**
     * For now credentials cannot be expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * This method enables to find token in users list of tokens.
     * @param token token to be searched
     * @return  if token exists return ConfirmationToken object, if not then return null
     */
    public ConfirmationToken findByTokenToken (String token) {
        for (ConfirmationToken lookingToken: this.tokenList) {
            if(lookingToken.getToken().equals(token)) {
                return lookingToken;
            }
        }
        return null;
    }
}
