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

    @Select("SELECT r.* FROM oao_role r INNER JOIN oao_user_role ur ON r.id = ur.role_id AND ur.user_id = #{userId} AND r.del = 0")
    List<OaoRole> findListByUserId(String userId);
}
