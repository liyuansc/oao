package com.liyu.oao.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyu.oao.uaa.dao.UserDao;
import com.liyu.oao.uaa.service.IUserService;
import com.liyu.oao.user.model.pojo.User;
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
    public User findByUsername(String username) {
        return super.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    }
}
