package com.oao.api.model;

import com.oao.user.model.po.Role;
import com.oao.user.model.po.User;

import java.util.List;

public class LoginUser extends User {
    private boolean login;
    private String clientId;
    private List<Role> roles;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
