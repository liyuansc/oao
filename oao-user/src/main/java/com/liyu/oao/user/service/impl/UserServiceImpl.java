package com.liyu.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyu.oao.user.constant.BeanName;
import com.liyu.oao.user.dao.UserDao;
import com.liyu.oao.user.model.po.User;
import com.liyu.oao.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Scheduler;

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
    @Qualifier(BeanName.DB_SCHEDULER)
    private Scheduler scheduler;
    
    @Override
    public User findByUsername(String username) {
        return super.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    }
}
