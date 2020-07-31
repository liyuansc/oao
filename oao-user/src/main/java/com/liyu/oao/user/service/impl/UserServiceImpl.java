package com.liyu.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyu.oao.user.cache.unit.impl.FindUserByUserNameUnit;
import com.liyu.oao.user.dao.UserDao;
import com.liyu.oao.user.model.po.User;
import com.liyu.oao.user.service.IUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Override
    @Cacheable(cacheNames = FindUserByUserNameUnit.NAME, key = "#p0")
    public User findByUsername(String username) {
        return super.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    }
}
