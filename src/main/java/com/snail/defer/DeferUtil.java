package com.snail.defer;

import org.springframework.core.NamedThreadLocal;

import java.util.function.Function;

/**
 * @version V1.0
 * @author: csz
 * @Title defer工具类 目标方法使用工具类来设置具体操作
 * @Package: com.snail.defer.util
 * @Description:
 * @date: 2021/04/11
 */
public class DeferUtil {

    private static ThreadLocal<DeferHolder> deferHolderThreadLocal =
        new NamedThreadLocal<>("DEFER_HOLDER_THREAD_LOCAL");

    public static void defer(DeferHolder.Defer defer) {
        DeferHolder holder = deferHolderThreadLocal.get();
        if (holder != null) {
            holder.add(defer);
        }
    }

    public static void recover(Function<Exception, Object> recoverFun) {
        DeferHolder holder = deferHolderThreadLocal.get();
        if (holder != null) {
            holder.setRecoverFun(recoverFun);
        }
    }

    static void initHolder() {
        deferHolderThreadLocal.set(new DeferHolder());
    }

    static DeferHolder removeAndGetHolder() {
        DeferHolder deferHolder = deferHolderThreadLocal.get();
        deferHolderThreadLocal.remove();
        return deferHolder;
    }

}
