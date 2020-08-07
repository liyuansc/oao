package com.oao.user.service;

import com.oao.user.model.po.OaoApi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyu
 * @since 2020-08-07
 */
public interface IOaoApiService extends IService<OaoApi> {

    List<OaoApi> findAll();
}
