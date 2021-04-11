package com.snail.defer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer.interceptor
 * @Description:
 * @date: 2021/04/11
 */
public class DeferMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object res = null;
        Exception err = null;
        try {
//            初始化存储操作的holder
            DeferUtil.initHolder();
            res = invocation.proceed();
        } catch (Exception e) {
//            保存异常信息
            err = e;
        } finally {
            DeferHolder deferHolder = DeferUtil.removeAndGetHolder();
//            执行延迟方法 这里会忽略延迟方法内的异常
            for (DeferHolder.Defer defer : deferHolder.getDeferList()) {
                try {
                    defer.run();
                } catch (Exception ignored) {
                }
            }
//            如果异常信息不为空
            if (err != null) {
//                无异常捕获方法 直接抛出
                if (deferHolder.getRecoverFun() == null) {
                    throw err;
                }
//                包装并返回
                return deferHolder.getRecoverFun().apply(err);
            }

        }
        return res;
    }

}
