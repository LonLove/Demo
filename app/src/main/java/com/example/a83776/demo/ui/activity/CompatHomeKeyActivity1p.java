package com.example.a83776.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.example.a83776.demo.R;
import com.example.a83776.demo.app.App;
import com.example.a83776.demo.base.BasePresenter;
import com.example.a83776.demo.base.BaseView;
import com.example.a83776.demo.di.component.ActivityComponent;
import com.example.a83776.demo.di.component.DaggerActivityComponent;
import com.example.a83776.demo.di.module.ActivityModule;
import com.example.a83776.demo.util.SnackBarUtil;

import javax.inject.Inject;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/30 10:54
 */
public abstract class CompatHomeKeyActivity1p<T extends BasePresenter> extends CompatHomeKeyActivity implements BaseView {
    @Inject
    protected T mPresenter;

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        SnackBarUtil.showLong(((ViewGroup) findViewById(R.id.content)).getChildAt(0),msg);
    }


    protected DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics=new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }
}
