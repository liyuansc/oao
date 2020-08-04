package com.liyu.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyu.oao.user.model.po.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
public interface IRoleService extends IService<Role> {
    List<Role> findListByUserId(String userId);
}
