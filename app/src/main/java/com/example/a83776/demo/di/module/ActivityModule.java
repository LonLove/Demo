package com.example.a83776.demo.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/31 16:36
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }
}
