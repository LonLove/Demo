package com.example.a83776.demo.base;

import android.widget.Toast;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.di.component.ActivityComponent;
import com.example.a83776.demo.di.component.DaggerActivityComponent;
import com.example.a83776.demo.di.module.ActivityModule;
import com.example.a83776.demo.util.LogUtils;

import javax.inject.Inject;

/**
 * description:
 * author: GaoJie
 * created at: 2018/7/31 12:09
*/
public abstract class BaseActivity1<T extends BasePresenter> extends SimpleActivity implements BaseView {


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

    protected abstract void initEventAndData();

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

    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        Toast.makeText(this, contentId, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass(), str);
    }
}
