package com.example.a83776.demo.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.a83776.demo.app.App;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/1 15:23
 */
public class SystemUtil {
    /**
     * 检查是否有可用网络
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
