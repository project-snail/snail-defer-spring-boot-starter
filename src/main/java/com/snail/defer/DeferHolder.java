package com.snail.defer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer.holder
 * @Description:
 * @date: 2021/04/11
 */
class DeferHolder {

    /**
     * 当前栈帧的名称 declaringClass:methodName
     */
    private final String curStackName;

    /**
     * 方法内定义的延迟操作
     */
    private List<Defer> deferList = new LinkedList<>();

    /**
     * 提供的异常捕获 入参为捕获的异常
     * PS: 注意返回值类型
     */
    private Function<Exception, Object> recoverFun;

    DeferHolder(String curStackName) {
        this.curStackName = curStackName;
    }

    Function<Exception, Object> getRecoverFun() {
        return recoverFun;
    }

    void setRecoverFun(Function<Exception, Object> recoverFun) {
        this.recoverFun = recoverFun;
    }

    public String getCurStackName() {
        return curStackName;
    }

    @FunctionalInterface
    public interface Defer {
        void run();
    }

    List<Defer> getDeferList() {
        return deferList;
    }

    void add(Defer defer) {
        deferList.add(defer);
    }


}
