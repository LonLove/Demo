package com.example.a83776.demo.widget;

import android.text.TextUtils;

import com.example.a83776.demo.base.BaseView;
import com.example.a83776.demo.model.http.exception.ApiException;
import com.example.a83776.demo.util.LogUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * description: 统一处理类似网络异常的问题
 * author: GaoJie
 * created at: 2018/5/26 16:04
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState;
    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view,String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view,String errorMsg,boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
       e.printStackTrace();
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null&& TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ApiException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("数据加载失败 '(⊙o⊙)？");
            HttpException httpException = (HttpException) e;
            LogUtils.e("CommonSubscriber", httpException.response().toString());
        } else {
            mView.showErrorMsg("未知错误'😡");
            LogUtils.d("CommonSubscriber",e.toString());
        }
        if (isShowErrorState ) {
            mView.stateError();
        }
    }
}
