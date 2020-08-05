package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.api.model.LoginUser;
import com.oao.user.cache.unit.impl.FindUserByUserNameUnit;
import com.oao.user.dao.UserDao;
import com.oao.user.model.po.Role;
import com.oao.user.model.po.User;
import com.oao.user.service.IRoleService;
import com.oao.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    @Cacheable(cacheNames = FindUserByUserNameUnit.NAME, key = "#p0")
    public User findByUsername(String username) {
        return super.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    }

    @Override
    public LoginUser findLoginUserByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            List<Role> roles = roleService.findListByUserId(user.getId());
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(user, loginUser);
            loginUser.setRoles(roles);
            return loginUser;
        }
        return null;
    }
}
