package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoRole;
import com.oao.user.model.po.OaoUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-06-23
 */
public interface IOaoUserService extends IService<OaoUser> {
    OaoUser findByUsername(String username);

    List<OaoUser> findListByMobile(String mobile);

    List<OaoUser> findListByUsername(String username);

    OaoLoginUser findLoginUserByUsername(String username);

    List<OaoRole> findRolesByUserId(String userId);

    boolean grant(String userId, List<String> roleIds);

    boolean saveUser(OaoUser oaoUser);
}
