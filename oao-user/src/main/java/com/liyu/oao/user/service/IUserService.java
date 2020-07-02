package com.liyu.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyu.oao.user.model.pojo.User;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
public interface IUserService extends IService<User> {
    Mono<User> findByUsernameMono(String username);
}
