package com.snail.defer;

import org.springframework.core.NamedThreadLocal;

import java.lang.reflect.Method;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
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

    private static ThreadLocal<Deque<DeferHolder>> deferHolderThreadLocal =
        new NamedThreadLocal<>("DEFER_HOLDER_THREAD_LOCAL");

    public static void defer(DeferHolder.Defer defer) {
        DeferHolder holder = getCurDeferHolder();
        if (holder != null) {
            holder.add(defer);
        }
    }

    public static void recover(Function<Exception, Object> recoverFun) {
        DeferHolder holder = getCurDeferHolder();
        if (holder != null) {
            holder.setRecoverFun(recoverFun);
        }
    }

    static void initHolder(Class<?> targetClass, Method targetMethod) {
        Deque<DeferHolder> deferHolderDeque = deferHolderThreadLocal.get();
        if (deferHolderDeque == null) {
            deferHolderDeque = new LinkedList<>();
            deferHolderThreadLocal.set(deferHolderDeque);
        }
        deferHolderDeque.addLast(new DeferHolder(targetClass.getName() + ":" + targetMethod.getName()));
    }

    /**
     * @return 获取当前调用DeferUtil的栈帧名称
     */
    private static String getCurStackName() {
        StackTraceElement curStack = new Throwable().getStackTrace()[3];
        return curStack.getClassName() + ":" + curStack.getMethodName();
    }

    /**
     * 获取时 会与当前栈顶的holder进行比较 确定是否为当前栈的holder
     * @return 当前栈的holder
     */
    private static DeferHolder getCurDeferHolder() {
        Deque<DeferHolder> deferHolderDeque = deferHolderThreadLocal.get();
        if (deferHolderDeque == null || deferHolderDeque.isEmpty()) {
            return null;
        }
        DeferHolder deferHolder = deferHolderDeque.peekLast();
        return Objects.equals(deferHolder.getCurStackName(), getCurStackName()) ? deferHolder : null;
    }

    static DeferHolder removeAndGetHolder() {
        Deque<DeferHolder> deferHolderDeque = deferHolderThreadLocal.get();
        DeferHolder deferHolder = deferHolderDeque.removeLast();
        if (deferHolderDeque.isEmpty()) {
            deferHolderThreadLocal.remove();
        }
        return deferHolder;
    }

}
