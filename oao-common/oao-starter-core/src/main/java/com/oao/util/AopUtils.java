package com.oao.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by liyu on 2017/8/31
 */
public class AopUtils extends org.springframework.aop.support.AopUtils {
    public static Method getMethod(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }

    public static Class<?> getDeclaringType(JoinPoint point) {
        return point.getSignature().getDeclaringType();
    }

    public static Method getOverrideMethod(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Method overrideMethod = ReflectionUtils.findMethod(point.getTarget().getClass(), signature.getName(), targetMethod.getParameterTypes());
        if (overrideMethod != null) {
            targetMethod = overrideMethod;
        }
        return targetMethod;
    }

    public static <A extends Annotation> A getAnnotation(JoinPoint point, Class<A> aClass) {
        A annotation = getOverrideMethod(point).getAnnotation(aClass);
        if (annotation == null) {
            annotation = getImplementType(point).getAnnotation(aClass);
        }
        return annotation;
    }

    public static Class<?> getImplementType(JoinPoint point) {
        return point.getTarget().getClass();
    }
}
