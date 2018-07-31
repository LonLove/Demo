package com.example.a83776.demo.base;

import android.content.Intent;

/**
 * description: presenter基类
 * author: GaoJie
 * created at: 2018/5/25 16:22
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();

    void onCreate();

    void onStart();//暂时没用到

    void onStop();

    void pause();//暂时没用到

    void attachIncomingIntent(Intent intent);//暂时没用到

}
