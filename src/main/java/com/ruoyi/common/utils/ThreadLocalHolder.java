package com.ruoyi.common.utils;


import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能：本地线程全局变量存储,支持父子线程之间的数据传递
 *
 * @author xx
 * @version 1.0.00  2020-01-07 13:15
 */
public final class ThreadLocalHolder {

    /**
     * 临时缓存线程变量
     */
    protected static final TransmittableThreadLocal<Map<String, Object>> LOCAL_VAR = new TransmittableThreadLocal<>();

    /**
     * 可传播的线程变量
     */
    protected static final TransmittableThreadLocal<Map<String, Object>> TRAN_VAR = new TransmittableThreadLocal<>();

    public static Map<String, Object> getTranVars() {
        return TRAN_VAR.get();
    }

    public static void begin() {
        if (LOCAL_VAR.get() == null) {
            LOCAL_VAR.set(new HashMap<String, Object>(16));
            TRAN_VAR.set(new HashMap<String, Object>(16));
        }
    }

    public static void begin(Map<String, Object> tranVar) {
        if (LOCAL_VAR.get() == null) {
            LOCAL_VAR.set(new HashMap<String, Object>(16));
            TRAN_VAR.set(new HashMap<String, Object>(16));
        }
        if (tranVar != null) {
            TRAN_VAR.get().putAll(tranVar);
        }
    }

    public static void end() {
        LOCAL_VAR.remove();
        TRAN_VAR.remove();
    }
}
