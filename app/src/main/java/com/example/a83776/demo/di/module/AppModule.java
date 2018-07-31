package com.example.a83776.demo.di.module;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.http.HttpHelper;
import com.example.a83776.demo.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 17:02
 */
@Module
public class AppModule {

    private final App application;

    /**
     * @param application {@link App#getAppComponent()}中的new AppModule{instance}完成实例化
     */
    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public App providerApplicationContext() {
        return application;
    }

    /**
     * retrofitHelper 由@Inject构造函数提供
     *
     * @param retrofitHelper
     * @return
     */
    @Provides
    @Singleton
    public HttpHelper providerHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    public DataManager providerDataManager(HttpHelper httpHelper) {
        return new DataManager(httpHelper);
    }
}
