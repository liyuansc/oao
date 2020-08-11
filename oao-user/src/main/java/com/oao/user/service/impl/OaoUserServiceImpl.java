package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.cache.unit.impl.FindUserByUserNameUnit;
import com.oao.user.dao.OaoUserDao;
import com.oao.user.model.po.OaoRole;
import com.oao.user.model.po.OaoUser;
import com.oao.user.service.IOaoRoleService;
import com.oao.user.service.IOaoUserService;
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
public class OaoUserServiceImpl extends ServiceImpl<OaoUserDao, OaoUser> implements IOaoUserService {
    @Autowired
    private IOaoUserService userService;
    @Autowired
    private IOaoRoleService roleService;

    @Override
    @Cacheable(cacheNames = FindUserByUserNameUnit.NAME, key = "#p0")
    public OaoUser findByUsername(String username) {
        return super.getOne(new QueryWrapper<OaoUser>().lambda().eq(OaoUser::getUsername, username));
    }

    @Override
    public OaoLoginUser findLoginUserByUsername(String username) {
        OaoUser user = userService.findByUsername(username);
        if (user != null) {
            List<OaoRole> roles = roleService.findListByUserId(user.getId());
            OaoLoginUser oaoLoginUser = new OaoLoginUser();
            BeanUtils.copyProperties(user, oaoLoginUser);
            oaoLoginUser.setRoles(roles);
            return oaoLoginUser;
        }
        return null;
    }
}
