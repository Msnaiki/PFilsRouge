package com.tasky.appManager.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.tasky.appManager.domaine.*;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private User user;
    private String role;

    // Constructor
    public CustomUserDetails(User user) {
        this.user = user;
        if (user instanceof Admin) {
            this.role = "ROLE_ADMIN";
        } else if (user instanceof Manager) {
            this.role = "ROLE_MANAGER";
        } else if (user instanceof Employee) {
            this.role = "ROLE_EMPLOYEE";
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Assuming User class has getPassword() method
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Assuming User class has getUsername() method
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    // Implement other methods (getPassword, getUsername, etc.)
    // ...

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}