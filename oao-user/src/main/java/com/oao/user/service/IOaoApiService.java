package com.oao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oao.user.model.po.OaoApi;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
public interface IOaoApiService extends IService<OaoApi> {

    List<OaoApi> findAll();

    //以api为单位，重新授权
    boolean grantByApi(String apiId, List<String> roleIds);

    //以角色为单位，重新授权
    boolean grantByRole(String roleId, List<String> apiIds);
}
