package com.synechron.usermanagement.auth.model;

import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byEmail) {
        this.username = byEmail.getEmail();
        this.password= byEmail.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        UserRole role = byEmail.getUserRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        auths.add(authority);
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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