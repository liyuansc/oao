package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.dao.OaoRoleApiDao;
import com.oao.user.model.po.OaoRoleApi;
import com.oao.user.service.IOaoRoleApiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
@Service
public class OaoRoleApiServiceImpl extends ServiceImpl<OaoRoleApiDao, OaoRoleApi> implements IOaoRoleApiService {

    @Override
    public List<OaoRoleApi> findAll() {
        return super.list();
    }

    @Override
    public boolean removeByRoleId(String roleId) {
        return super.remove(new QueryWrapper<OaoRoleApi>().lambda().eq(OaoRoleApi::getRoleId, roleId));
    }

    @Override
    public boolean removeByApiId(String apiId) {
        return super.remove(new QueryWrapper<OaoRoleApi>().lambda().eq(OaoRoleApi::getApiId, apiId));
    }
}
