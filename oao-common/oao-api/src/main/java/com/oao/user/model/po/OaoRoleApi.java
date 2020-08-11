package com.oao.user.model.po;

import com.oao.common.model.SuperPo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
@Data
@NoArgsConstructor
public class OaoRoleApi extends SuperPo {

    public OaoRoleApi(String roleId, String apiId) {
        this.roleId = roleId;
        this.apiId = apiId;
    }

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String apiId;
}
