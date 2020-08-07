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
public class OaoUserRole extends SuperPo {

    private static final long serialVersionUID = 1L;

    private String userId;

    private Long roleId;
}
