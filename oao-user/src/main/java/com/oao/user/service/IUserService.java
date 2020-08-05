package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.api.model.LoginUser;
import com.oao.user.model.po.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
public interface IUserService extends IService<User> {
    User findByUsername(String username);

    LoginUser findLoginUserByUsername(String username);
}
