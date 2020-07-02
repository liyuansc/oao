package com.liyu.oao.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyu.oao.user.model.pojo.User;

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
}
