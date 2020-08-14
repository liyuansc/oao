package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.dao.OaoUserRoleDao;
import com.oao.user.model.po.OaoUserRole;
import com.oao.user.service.IOaoUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
@Service
public class OaoUserRoleServiceImpl extends ServiceImpl<OaoUserRoleDao, OaoUserRole> implements IOaoUserRoleService {

    @Override
    public boolean removeByUserId(String userId) {
        return super.remove(new QueryWrapper<OaoUserRole>().lambda().eq(OaoUserRole::getUserId, userId));
    }
}
