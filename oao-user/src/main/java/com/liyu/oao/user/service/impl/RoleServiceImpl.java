package com.liyu.oao.user.service.impl;

import com.liyu.oao.user.model.po.Role;
import com.liyu.oao.user.dao.RoleDao;
import com.liyu.oao.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyu
 * @since 2020-07-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

}
