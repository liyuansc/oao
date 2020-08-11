package com.oao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.cache.unit.impl.FindAllApiUnit;
import com.oao.user.dao.OaoApiDao;
import com.oao.user.model.po.OaoApi;
import com.oao.user.model.po.OaoRoleApi;
import com.oao.user.service.IOaoApiService;
import com.oao.user.service.IOaoRoleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
@Service
public class OaoApiServiceImpl extends ServiceImpl<OaoApiDao, OaoApi> implements IOaoApiService {
    @Autowired
    private IOaoRoleApiService oaoRoleApiService;

    @Override
    @Cacheable(cacheNames = FindAllApiUnit.NAME, key = "'all'")
    public List<OaoApi> findAll() {
        List<OaoApi> oaoApis = super.list();
        Map<String, List<OaoRoleApi>> roleApiMap = oaoRoleApiService.findAll().stream().collect(Collectors.groupingBy(OaoRoleApi::getApiId));
        oaoApis.forEach(oaoApi -> {
            List<String> roleIds = roleApiMap.getOrDefault(oaoApi.getId(), Collections.emptyList()).stream().map(OaoRoleApi::getRoleId).collect(Collectors.toList());
            oaoApi.setRoleIds(roleIds);
        });
        return oaoApis;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = FindAllApiUnit.NAME, allEntries = true)
    public boolean grantByApi(String apiId, List<String> roleIds) {
        //删除之前的角色关联
        oaoRoleApiService.remove(new QueryWrapper<OaoRoleApi>().lambda().eq(OaoRoleApi::getApiId, apiId));
        oaoRoleApiService.saveBatch(roleIds.stream().map(roleId -> new OaoRoleApi(roleId, apiId)).collect(Collectors.toList()));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = FindAllApiUnit.NAME, allEntries = true)
    public boolean grantByRole(String roleId, List<String> apiIds) {
        //删除之前的角色关联
        oaoRoleApiService.remove(new QueryWrapper<OaoRoleApi>().lambda().eq(OaoRoleApi::getRoleId, roleId));
        oaoRoleApiService.saveBatch(apiIds.stream().map(apiId -> new OaoRoleApi(roleId, apiId)).collect(Collectors.toList()));
        return true;
    }
}
