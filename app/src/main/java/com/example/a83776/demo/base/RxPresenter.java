package com.example.a83776.demo.base;

import android.content.Intent;

import com.example.a83776.demo.util.LogUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description: 基于Rx的Presenter封装，控制订阅的生命周期
 * author: GaoJie
 * created at: 2018/5/26 9:48
 */
public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addSubscribe(Disposable subscription) {
        if (mView != null) {
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        LogUtils.d(getClass().getSimpleName(), "detachView");
        this.mView = null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public T getView() {
        return mView;
    }
}
