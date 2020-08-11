package com.oao.user.model;

import com.oao.user.model.po.OaoUser;
import lombok.Data;

@Data
public class OaoLoginUser extends OaoUser {
    private boolean login;
    private String clientId;
}
