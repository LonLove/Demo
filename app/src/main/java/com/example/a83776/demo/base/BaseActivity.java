package com.example.a83776.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.di.component.ActivityComponent;
import com.example.a83776.demo.di.component.DaggerActivityComponent;
import com.example.a83776.demo.di.module.ActivityModule;
import com.example.a83776.demo.util.LogUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * description: activity基类
 * author: GaoJie
 * created at: 2018/5/25 16:17
 */
public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity implements BaseView {

    private View mView;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        ButterKnife.bind(this);
        onViewCreate();
        initEventAndData();
    }

    protected abstract void initEventAndData();


    /**
     * @return 显示的内容
     */
    private View getView() {
        mView = View.inflate(this, getLayoutId(), null);
        return mView;
    }

    protected abstract int getLayoutId();

    protected void onViewCreate() {
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
