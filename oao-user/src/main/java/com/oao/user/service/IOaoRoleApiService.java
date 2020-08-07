package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.po.OaoRoleApi;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
public interface IOaoRoleApiService extends IService<OaoRoleApi> {

    List<OaoRoleApi> findAll();
}
