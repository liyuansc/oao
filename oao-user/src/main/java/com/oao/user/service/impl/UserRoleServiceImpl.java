package com.oao.user.service.impl;

import com.oao.user.model.po.UserRole;
import com.oao.user.dao.UserRoleDao;
import com.oao.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {

}
