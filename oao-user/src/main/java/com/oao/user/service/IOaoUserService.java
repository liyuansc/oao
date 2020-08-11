package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
public interface IOaoUserService extends IService<OaoUser> {
    OaoUser findByUsername(String username);

    OaoLoginUser findLoginUserByUsername(String username);
}
