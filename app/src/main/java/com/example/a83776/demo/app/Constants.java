package com.example.a83776.demo.app;

import android.os.Environment;

import java.io.File;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/1 15:01
 */
public class Constants {
    //===============PATH===========
   public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
   public static final String PATH_CACHE = PATH_DATA + "/NetCache";
   public static final String SP_NO_IMAGE ="no_image";
   public static final String path_sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demo";
    public static final Integer NETEASE_ROLE = 1;//Tv端在ruankoId加后缀“0”发送接收指令，HD 1
    //网易IM登录密码
    public static final String NETEASE_PWD = "netease_kocla_6th";
//    public static final String NETEASE_PWD = "GJ1994";
}
