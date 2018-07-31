package com.example.a83776.demo.di.module;

import android.app.Activity;

import com.example.a83776.demo.base.BaseFragment;
import com.example.a83776.demo.di.scope.FramentScope;

import dagger.Module;
import dagger.Provides;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/25 14:55
 */
@Module
public class FragmentModule {
    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FramentScope
    public Activity providerActivity() {
        return mFragment.getActivity();
    }
}
