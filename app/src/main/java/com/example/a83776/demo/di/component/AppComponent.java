package com.example.a83776.demo.di.component;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.di.module.AppModule;
import com.example.a83776.demo.di.module.HttpModule;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.Prefs.ImplPreferencesHelper;
import com.example.a83776.demo.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 17:24
*/
@Singleton
@Component(modules ={AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();//提供App的context
    DataManager getDataManager();//数据中心
    RetrofitHelper retrofitHelper();//提供http的帮助类

    ImplPreferencesHelper preferencesHelper();
}
