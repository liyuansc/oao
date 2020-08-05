package com.oao.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oao.user.model.po.Role;
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
public interface RoleDao extends BaseMapper<Role> {

    @Select("SELECT r.* FROM role r INNER JOIN user_role ur ON r.id = ur.role_id AND ur.user_id = #{userId}")
    List<Role> findListByUserId(String userId);
}
