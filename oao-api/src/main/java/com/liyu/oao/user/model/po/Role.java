package com.liyu.oao.user.model.po;

import com.liyu.oao.common.model.SuperPo;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyu
 * @since 2020-07-31
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

    @Override
    public String toString() {
        return "Role{" +
            "code=" + code +
            ", name=" + name +
        "}";
    }
}
