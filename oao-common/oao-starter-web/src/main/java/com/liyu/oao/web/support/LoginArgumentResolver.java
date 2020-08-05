package com.liyu.oao.web.support;

import com.liyu.oao.api.annotation.Login;
import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.constant.OaoSecurityConstant;
import com.liyu.oao.common.model.OaoGrantedAuthority;
import com.liyu.oao.user.feign.UserClient;
import com.liyu.oao.user.model.po.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Token转化SysUser
 *
 * @author zlt
 * @date 2018/12/21
 */
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private UserClient userClient;

    public LoginArgumentResolver() {
    }

    public LoginArgumentResolver(UserClient userClient) {
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
        String username = request.getHeader(OaoSecurityConstant.HttpHeader.I_USERNAME);
        LoginUser loginUser = new LoginUser();
        if (StringUtils.isEmpty(username)) {
            loginUser.setLogin(false);
        } else {
            String userId = request.getHeader(OaoSecurityConstant.HttpHeader.I_USER_ID);
            String clientId = request.getHeader(OaoSecurityConstant.HttpHeader.I_CLIENT_ID);
            if (isFull && userClient != null) {
                LoginUser full = userClient.findLoginUserByUsername(username).check().getData();
                BeanUtils.copyProperties(full, loginUser);
            } else {
                String authorities = request.getHeader(OaoSecurityConstant.HttpHeader.I_AUTHORITIES);
                List<Role> roles = Arrays.stream(authorities.split(",")).map(a -> {
                    OaoGrantedAuthority authority = OaoGrantedAuthority.parse(a);
                    Role role = new Role();
                    role.setId(authority.getId());
                    role.setCode(authority.getCode());
                    return role;
                }).collect(Collectors.toList());
                loginUser.setRoles(roles);
            }
            loginUser.setLogin(true);
            loginUser.setId(userId);
            loginUser.setUsername(username);
            loginUser.setClientId(clientId);
        }
        return loginUser;
    }

    public UserClient getUserClient() {
        return userClient;
    }

    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }
}