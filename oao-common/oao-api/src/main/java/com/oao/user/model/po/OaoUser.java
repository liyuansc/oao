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
 * @since 2020-06-23
 */
@Data
public class OaoUser extends SuperPo {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String nickname;

    private String headImgUrl;

    private String mobile;

    private Boolean sex;

    private Boolean enabled;

    private String type;

    private String company;

    private String openId;

    @TableField(exist = false)
    private List<OaoRole> roles;

}
