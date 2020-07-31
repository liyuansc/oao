package com.liyu.oao.web.config;

import com.liyu.oao.api.annotation.Login;
import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.constant.OaoSecurityConstant;
import com.liyu.oao.user.feign.IUserClient;
import com.liyu.oao.user.model.po.User;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Token转化SysUser
 *
 * @author zlt
 * @date 2018/12/21
 */
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private IUserClient userClient;

    public LoginArgumentResolver() {
    }

    public LoginArgumentResolver(IUserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        boolean flag = methodParameter.hasParameterAnnotation(Login.class)
                && methodParameter.getParameterType().equals(LoginUser.class);
        return flag;
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        Login login = methodParameter.getParameterAnnotation(Login.class);
        boolean isFull = login.isFull();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String userId = request.getHeader(OaoSecurityConstant.HttpHeader.I_USER_ID);
        String username = request.getHeader(OaoSecurityConstant.HttpHeader.I_USERNAME);
        LoginUser loginUser = new LoginUser();
        if (StringUtils.isEmpty(username)) {
            loginUser.setLogin(false);
        } else {
            loginUser.setLogin(true);
            loginUser.setUserId(userId);
            loginUser.setUsername(username);
            if (isFull && userClient != null) {
                User user = userClient.findUserByUsername(username).check().getData();
                loginUser.setUser(user);
            }
        }
        return loginUser;
    }
}
