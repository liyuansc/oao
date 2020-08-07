package com.oao.user.model;

import com.oao.user.model.po.OaoRole;
import com.oao.user.model.po.OaoUser;
import lombok.Data;

import java.util.List;

@Data
public class LoginUser extends OaoUser {
    private boolean login;
    private String clientId;
}
