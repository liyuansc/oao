package com.oao.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.cache.unit.impl.FindRoleByUserIdUnit;
import com.oao.user.dao.RoleDao;
import com.oao.user.model.po.Role;
import com.oao.user.service.IRoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

    @Override
    @Cacheable(cacheNames = FindRoleByUserIdUnit.NAME, key = "#p0")
    public List<Role> findListByUserId(String userId) {
        return this.getBaseMapper().findListByUserId(userId);
    }
}
