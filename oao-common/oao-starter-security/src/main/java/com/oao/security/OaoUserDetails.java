package com.oao.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class OaoUserDetails implements UserDetails {
    private String id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public OaoUserDetails() {
    }

    public OaoUserDetails(String username) {
        this.username = username;
    }

    public OaoUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public OaoUserDetails(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public OaoUserDetails(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities != null) {
            return this.authorities;
        }
        return AuthorityUtils.createAuthorityList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
