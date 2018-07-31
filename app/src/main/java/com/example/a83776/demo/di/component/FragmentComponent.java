package com.example.a83776.demo.di.component;

import android.app.Activity;

import com.example.a83776.demo.di.module.FragmentModule;
import com.example.a83776.demo.di.scope.FramentScope;
import com.example.a83776.demo.ui.fragment.LivesFragment;

import dagger.Component;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/25 14:59
*/
@FramentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
     Activity getActivity();
    void inject(LivesFragment fragment);

//    void inject(LiveFragment2 fragment2);
}
