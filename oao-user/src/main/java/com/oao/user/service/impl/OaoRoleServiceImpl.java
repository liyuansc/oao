package com.oao.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oao.user.dao.OaoRoleDao;
import com.oao.user.model.po.OaoRole;
import com.oao.user.service.IOaoRoleService;
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
    public List<OaoRole> findListByUserId(String userId) {
        return this.getBaseMapper().findListByUserId(userId);
    }
}
