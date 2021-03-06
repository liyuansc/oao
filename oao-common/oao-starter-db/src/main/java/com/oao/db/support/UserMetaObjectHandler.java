package com.oao.db.support;

import com.oao.common.constant.OaoSecurityConstant;
import com.oao.common.util.RequestUtils;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.support.OaoLoginUserSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class UserMetaObjectHandler extends DateMetaObjectHandler {
    private final static String CREATE_USER = "createUser";
    private final static String UPDATE_USER = "updateUser";
    @Autowired(required = false)
    private OaoLoginUserSupport loginArgumentResolver;

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        super.insertFill(metaObject);
        if (loginArgumentResolver == null) return;
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) return;
        OaoLoginUser user = loginArgumentResolver.getUser(request, false);
        if (user == null || user.isLogin() == false) return;
        String username = user.getUsername();
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
        super.updateFill(metaObject);
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
