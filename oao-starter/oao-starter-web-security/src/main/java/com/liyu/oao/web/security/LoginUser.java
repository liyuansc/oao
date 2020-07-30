package com.liyu.oao.web.security;

import com.liyu.oao.user.model.pojo.User;

import javax.management.relation.Role;
import java.util.List;

public class LoginUser {
    private boolean login;
    private String userId;
    private String username;
    private User user;
    private List<Role> roles;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
