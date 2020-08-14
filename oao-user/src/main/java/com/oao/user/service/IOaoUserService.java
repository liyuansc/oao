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
    List<OaoUser> findListByMobile(String mobile);

    List<OaoUser> findListByUsername(String username);

    OaoUser findById(String userId);

    OaoLoginUser findLoginUser(String userId);

    OaoLoginUser findLoginUserByUsername(String userId);

    List<OaoRole> findRolesByUserId(String userId);

    boolean grant(String userId, List<String> roleIds);

    OaoUser saveUser(OaoUser oaoUser);
}
