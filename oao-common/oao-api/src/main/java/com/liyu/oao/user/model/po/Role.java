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
public class Role extends SuperPo {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 租户id
     */
    private String tenantId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "Role{" +
            "code=" + code +
            ", name=" + name +
            ", tenantId=" + tenantId +
        "}";
    }
}
