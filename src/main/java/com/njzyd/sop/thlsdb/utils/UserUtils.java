package com.njzyd.sop.thlsdb.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.MDC;

/**
 * @author
 * @date 2017/11/20
 * 用户相关工具类
 */
public class UserUtils {
    private static final ThreadLocal<String> THREAD_LOCAL_USER = new TransmittableThreadLocal<>();
    private static final String KEY_USER = "user";


    /**
     * 设置用户信息
     *
     * @param userId
     */
    public static void setUser(String userId) {
        THREAD_LOCAL_USER.set(userId);
        MDC.put(KEY_USER, userId);
    }

    public static String getUserId() {
        return THREAD_LOCAL_USER.get();
    }

    public static void clearAllUserInfo() {
        THREAD_LOCAL_USER.remove();
        MDC.remove(KEY_USER);
    }
}
