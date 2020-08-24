package com.oao.web.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.oao.common.constant.OaoSecurityConstant;
import com.oao.common.util.UrlCoder;
import com.oao.user.annotation.OaoLogin;
import com.oao.user.feign.UserClient;
import com.oao.user.model.OaoLoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@Data
@NoArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private UserClient userClient;

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
        boolean flag = methodParameter.hasParameterAnnotation(OaoLogin.class)
                && methodParameter.getParameterType().equals(OaoLoginUser.class);
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
        OaoLogin oaoLogin = methodParameter.getParameterAnnotation(OaoLogin.class);
        boolean isFull = oaoLogin.isFull();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String iUser = request.getHeader(OaoSecurityConstant.HttpHeader.I_USER);
        OaoLoginUser loginUser;
        if (StringUtils.isEmpty(iUser)) {
            loginUser = new OaoLoginUser();
            loginUser.setLogin(false);
        } else {
            loginUser = JSON.parseObject(UrlCoder.decode(iUser), new TypeReference<OaoLoginUser>() {
            });
            String userId = loginUser.getId();
            if (isFull && userClient != null) {
                OaoLoginUser full = userClient.findLoginUser(userId).check().getData();
                BeanUtils.copyProperties(full, loginUser);
            }
            loginUser.setLogin(true);
            loginUser.setPassword(null);
        }
        return loginUser;
    }
}
