package com.example.a83776.demo.di.component;

import com.example.a83776.demo.di.module.ActivityModule;
import com.example.a83776.demo.di.scope.ActivityScope;
import com.example.a83776.demo.ui.activity.LiveDetailActivity;
import com.example.a83776.demo.ui.activity.LoginActivity;

import dagger.Component;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/31 16:38
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity activity);

    void inject(LiveDetailActivity activity);
}
