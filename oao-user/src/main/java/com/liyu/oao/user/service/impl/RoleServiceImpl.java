package com.liyu.oao.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyu.oao.user.dao.RoleDao;
import com.liyu.oao.user.model.po.Role;
import com.liyu.oao.user.service.IRoleService;
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
    public List<Role> findListByUserId(String userId) {
        return this.getBaseMapper().findListByUserId(userId);
    }
}
