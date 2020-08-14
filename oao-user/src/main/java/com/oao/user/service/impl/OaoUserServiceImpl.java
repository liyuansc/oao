package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.common.constant.ResultCode;
import com.oao.common.exception.ResultException;
import com.oao.user.cache.unit.impl.FindRoleByUserIdUnit;
import com.oao.user.cache.unit.impl.FindUserByUserNameUnit;
import com.oao.user.dao.OaoUserDao;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoRole;
import com.oao.user.model.po.OaoUser;
import com.oao.user.model.po.OaoUserRole;
import com.oao.user.service.IOaoRoleService;
import com.oao.user.service.IOaoUserRoleService;
import com.oao.user.service.IOaoUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IOaoUserRoleService userRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(cacheNames = FindUserByUserNameUnit.NAME, key = "#p0")
    public OaoUser findByUsername(String username) {
        return super.getOne(new QueryWrapper<OaoUser>().lambda().eq(OaoUser::getUsername, username));
    }

    @Override
    public List<OaoUser> findListByMobile(String mobile) {
        return super.list(new QueryWrapper<OaoUser>().lambda().eq(OaoUser::getMobile, mobile));
    }

    @Override
    public List<OaoUser> findListByUsername(String username) {
        return super.list(new QueryWrapper<OaoUser>().lambda().eq(OaoUser::getUsername, username));
    }

    @Override
    public OaoLoginUser findLoginUserByUsername(String username) {
        OaoUser user = userService.findByUsername(username);
        if (user != null) {
            List<OaoRole> roles = userService.findRolesByUserId(user.getId());
            user.setRoles(roles);
            OaoLoginUser loginUser = new OaoLoginUser();
            BeanUtils.copyProperties(user, loginUser);
            return loginUser;
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = FindRoleByUserIdUnit.NAME, key = "#p0")
    public List<OaoRole> findRolesByUserId(String userId) {
        return roleService.findListByUserId(userId);
    }

    @Override
    @CacheEvict(cacheNames = FindRoleByUserIdUnit.NAME, key = "#p0")
    public boolean grant(String userId, List<String> roleIds) {
        userRoleService.removeByUserId(userId);
        List<OaoUserRole> userRoles = roleIds.stream().map(roleId -> new OaoUserRole(userId, roleId)).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(OaoUser user) {
        //不允许前端传id
        boolean isUpdate = StringUtils.isNotBlank(user.getId());
        String username = user.getUsername();
        String mobile = user.getMobile();
        boolean repeatUsername = userService.findListByUsername(username).stream().anyMatch(u -> isUpdate ? !StringUtils.equals(u.getId(), user.getId()) : true);
        if (repeatUsername) {
            throw new ResultException(ResultCode.R4010.build());
        }
        boolean repeatMobile = userService.findListByMobile(mobile).stream().anyMatch(u -> isUpdate ? !StringUtils.equals(u.getId(), user.getId()) : true);
        if (repeatMobile) {
            throw new ResultException(ResultCode.R4011.build());
        }
        //密码逻辑
        String rawPassword = user.getPassword();
        if (StringUtils.isNotBlank(rawPassword)) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);
        }
        if (isUpdate) {
            super.updateById(user);
        } else {
            super.save(user);
        }
        String userId = user.getId();
        List<OaoRole> roles = user.getRoles();
        if (roles != null) {
            List<String> roleIds = roles.stream().map(OaoRole::getId).collect(Collectors.toList());
            //授权
            userService.grant(userId, roleIds);
        }
        return true;
    }
}
