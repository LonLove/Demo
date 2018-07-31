package com.example.a83776.netease.im.util;

/**
 * Created by admin on 2017/5/10.
 */

public class IMServers {
//    private static final String API_SERVER = "https://api.netease.im/nimserver/";
    private static final String API_SERVER = "http://api.netease.im/nimserver/";
    /**
     * 创建网易云通信ID
     */
    public static final String CREATE = API_SERVER + "user/create.action";
    /**
     * 更新并获取新token
     */
    public static final String UPDATE = API_SERVER + "user/update.action";
    /**
     * 更新并获取新token
     */
    public static final String REFRESHTOKEN = API_SERVER + "user/refreshToken.action";


}
