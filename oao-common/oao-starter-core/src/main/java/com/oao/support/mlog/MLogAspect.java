package com.oao.support.mlog;

/**
 * Created by liyu on 2017/8/1
 */

import com.alibaba.fastjson.JSON;
import com.oao.util.AopUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 系统日志切面
 * 可打印方法的参数,返回值等
 *
 * @author liyu
 */
@Aspect
public class MLogAspect {
    public final static String P = "p";


    @Pointcut("@annotation(com.oao.support.mlog.MLog)")
    private void pointCut() {
    }//定义一个切入点

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MLog mLog = AopUtils.getAnnotation(point, MLog.class);
        Log log = LogFactory.getLog(AopUtils.getDeclaringType(point));
        String title = mLog.title();
        if (StringUtils.isEmpty(title)) {
            Method m = AopUtils.getOverrideMethod(point);
            title = String.join(".", m.getName());
        }
        Object[] args = point.getArgs();
        List<MArg> mArgs = Arrays.stream(mLog.value()).collect(Collectors.toList());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        if (args.length == 1) evaluationContext.setRootObject(args[0]);
        IntStream.range(0, args.length).forEach(i -> evaluationContext.setVariable(P + i, args[i]));
        //打印方法参数
        Map<Boolean, List<MArg>> isBeforeMArgs = mArgs.stream().collect(Collectors.groupingBy(mArg -> !(mArg.value().indexOf("#result") != -1)));
        List<MArg> beforeMArgs = isBeforeMArgs.getOrDefault(true, Collections.emptyList());
        if (beforeMArgs.size() > 0) {
            String beforeMsg = MessageFormat.format("{0}-before: [{1}]", title, formatArgs(beforeMArgs, evaluationContext, log));
            if (mLog.debug()) log.debug(beforeMsg);
            else log.info(beforeMsg);
        }
        Object result;
        try {
            result = point.proceed();
            evaluationContext.setVariable("result", result);
            //打印返回值参数
            List<MArg> afterMArgs = isBeforeMArgs.getOrDefault(false, new ArrayList<>());
            if (afterMArgs.size() > 0) {
                String afterMsg = MessageFormat.format("{0}-after: [{1}]", title, formatArgs(afterMArgs, evaluationContext, log));
                if (mLog.debug()) log.debug(afterMsg);
                else log.info(afterMsg);
            }
        } catch (Exception e) {
            if (mLog.afterThrow()) {
                log.error(MessageFormat.format("{0}-error", title), e);
            }
            throw e;
        }
        return result;
    }

    private String formatArgs(List<MArg> mArgs, EvaluationContext evaluationContext, Log log) {
        return IntStream.range(0, mArgs.size()).mapToObj(i -> {
            MArg mArg = mArgs.get(i);
            String key = mArg.key();
            if (StringUtils.isBlank(key)) key = "a" + i;
            return String.join("=", key, expression2Value(mArg.value(), evaluationContext, log));
        }).collect(Collectors.joining(", "));
    }

    private String expression2Value(String str, EvaluationContext evaluationContext, Log log) {
        Expression expression = new SpelExpressionParser().parseExpression(str);
        Object value = null;
        try {
            value = expression.getValue(evaluationContext);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String vStr;
        if (value == null) {
            vStr = null;
        } else if (value instanceof String) {
            vStr = (String) value;
        } else {
            try {
                vStr = JSON.toJSONString(value);
            } catch (Exception e) {
                vStr = "JSONParseError";
            }
        }
        return vStr;
    }
}
