package com.liyu.oao.user.model;

import com.liyu.oao.user.model.po.Role;

import javax.validation.constraints.NotBlank;

public class AddRoleReq extends Role {

    @NotBlank
    @Override
    public String getCode() {
        return super.getCode();
    }


    @NotBlank
    @Override
    public String getName() {
        return super.getName();
    }

    @NotBlank
    @Override
    public String getTenantId() {
        return super.getTenantId();
    }
}
