package com.epra.wiki.util;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/15 5:28 下午
 * @Description: TODO
 */

import java.io.Serializable;

public class RequestContext implements Serializable {

    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        RequestContext.remoteAddr.set(remoteAddr);
    }

}
