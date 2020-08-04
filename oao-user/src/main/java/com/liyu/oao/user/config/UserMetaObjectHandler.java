package com.liyu.oao.user.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.liyu.oao.common.constant.OaoSecurityConstant;
import com.liyu.oao.web.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import javax.servlet.http.HttpServletRequest;

public class UserMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_USER = "createUser";
    private final static String UPDATE_USER = "updateUser";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) return;
        String username = request.getHeader(OaoSecurityConstant.HttpHeader.I_USERNAME);
        if (StringUtils.isBlank(username)) return;
        Object createUser = getFieldValByName(CREATE_USER, metaObject);
        Object updateUser = getFieldValByName(UPDATE_USER, metaObject);
        if (createUser == null || updateUser == null) {
            if (createUser == null) {
                setFieldValByName(CREATE_USER, username, metaObject);
            }
            if (updateUser == null) {
                setFieldValByName(UPDATE_USER, username, metaObject);
            }
        }
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //mybatis-plus版本2.0.9+
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) return;
        String username = request.getHeader(OaoSecurityConstant.HttpHeader.I_USERNAME);
        if (StringUtils.isBlank(username)) return;
        Object updateUser = getFieldValByName(UPDATE_USER, metaObject);
        if (updateUser == null) {
            setFieldValByName(UPDATE_USER, username, metaObject);
        }
    }
}
