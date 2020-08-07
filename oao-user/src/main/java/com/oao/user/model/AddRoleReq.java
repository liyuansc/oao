package com.oao.user.model;

import com.oao.user.model.po.OaoRole;

import javax.validation.constraints.NotBlank;

public class AddRoleReq extends OaoRole {

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
