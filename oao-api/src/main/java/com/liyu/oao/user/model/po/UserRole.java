package com.liyu.oao.user.model.po;

import com.liyu.oao.common.model.SuperPo;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
public class UserRole extends SuperPo {

    private static final long serialVersionUID = 1L;

    private String userId;

    private Long roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
            "userId=" + userId +
            ", roleId=" + roleId +
        "}";
    }
}
