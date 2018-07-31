package com.example.a83776.demo.model.http.api;

import com.example.a83776.demo.app.Config;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 14:20
 */
public class ApiConfig {
    //这里切换环境
    private static final int SERVER_RELEASE = Config.SERVER_RELEASE;
    static String BASE_DEMO;//demo服务器
    static String BASE_KOCLA;//基础服务器(考拉中心库)
    static  String BASE_BEIKE ;//备课服务器

    static {
        if (SERVER_RELEASE == 1) {//正式服务器
            BASE_KOCLA = "http://api.beike.kocla.com/kocla-api/";
            BASE_BEIKE = "http://api.beike.kocla.com:8080/marketGateway/";
            BASE_DEMO = "https://api.douban.com/v2/";
        } else if (SERVER_RELEASE == 2) {//测试环境
            BASE_KOCLA = "http://218.17.158.37:8880/kocla-api/";
            BASE_BEIKE = "http://218.17.158.37:8880/marketGateway/";
            BASE_DEMO = "https://api.douban.com/v2/";
        } else if (SERVER_RELEASE == 3) {//演示环境
            BASE_KOCLA = "http://218.17.158.37:8233/kocla-api/";
            BASE_BEIKE = "http://218.17.158.37:8233/marketGateway/";
            BASE_DEMO = "https://api.douban.com/v2/";
        } else if (SERVER_RELEASE == 4) {//开发环境
            BASE_DEMO = "https://api.douban.com/v2/";
            BASE_KOCLA = "http://218.17.158.37:8147/kocla-api/";
            BASE_BEIKE = "http://218.17.158.37:8147/marketGateway/";
        }
    }


}
