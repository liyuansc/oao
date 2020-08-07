package com.oao.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.cache.unit.impl.FindRoleByUserIdUnit;
import com.oao.user.dao.OaoRoleDao;
import com.oao.user.model.po.OaoRole;
import com.oao.user.service.IOaoRoleService;
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
public class OaoRoleServiceImpl extends ServiceImpl<OaoRoleDao, OaoRole> implements IOaoRoleService {

    @Override
    @Cacheable(cacheNames = FindRoleByUserIdUnit.NAME, key = "#p0")
    public List<OaoRole> findListByUserId(String userId) {
        return this.getBaseMapper().findListByUserId(userId);
    }
}
