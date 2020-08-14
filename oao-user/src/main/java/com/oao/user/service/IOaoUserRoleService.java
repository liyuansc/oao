package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.po.OaoUserRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
public interface IOaoUserRoleService extends IService<OaoUserRole> {

    boolean removeByUserId(String userId);
}
