package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.po.OaoRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
public interface IOaoRoleService extends IService<OaoRole> {
    List<OaoRole> findListByUserId(String userId);
}
