package com.oao.user.model.po;

import com.oao.common.model.SuperPo;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
@Data
public class OaoRole extends SuperPo {

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
}
