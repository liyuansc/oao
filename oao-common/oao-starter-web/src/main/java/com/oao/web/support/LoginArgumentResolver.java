package com.oao.web.support;

import com.oao.user.annotation.OaoLogin;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.support.OaoLoginUserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private OaoLoginUserSupport loginUserSupport;

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
        return loginUserSupport.getUser(request, isFull);
    }
}
