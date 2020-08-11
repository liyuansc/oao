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

    //给api赋予重新分配角色
    boolean grantApi(OaoApi api);
}
