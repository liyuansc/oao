package com.oao.user.model.po;

import com.oao.common.model.SuperPo;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
@Data
public class OaoRoleApi extends SuperPo {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String apiId;
}
