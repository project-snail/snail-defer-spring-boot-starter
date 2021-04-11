package com.snail.defer.advisor;

import com.snail.defer.DeferMethodInterceptor;
import com.snail.defer.annotation.Deferrable;
import com.snail.defer.config.SnailDeferProperties;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer.advisor
 * @Description:
 * @date: 2021/04/11
 */
public class DeferPointcutAdvisor extends AbstractPointcutAdvisor {

    private final Pointcut pointcut;

    private final Advice advice;

    public DeferPointcutAdvisor(SnailDeferProperties properties) {
        pointcut = new ComposablePointcut(new AnnotationMatchingPointcut(null, Deferrable.class))
            .union(new AnnotationMatchingPointcut(Deferrable.class));
        advice = new DeferMethodInterceptor();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

}
