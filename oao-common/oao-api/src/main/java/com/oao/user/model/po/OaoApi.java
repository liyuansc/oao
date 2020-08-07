package com.oao.user.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.oao.common.model.SuperPo;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
@Data
public class OaoApi extends SuperPo {

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
     * 路径
     */
    private String uri;

    /**
     * 1-匿名资源，2-登录资源，3-角色资源
     */
    private Integer type;

    @TableField(exist = false)
    private List<String> roleIds;
}
