package com.oao.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oao.user.model.po.OaoRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liyu
 * @since 2020-08-03
 */
public interface OaoRoleDao extends BaseMapper<OaoRole> {

    List<OaoRole> findListByUserId(String userId);
}
