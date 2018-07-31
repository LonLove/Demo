package com.example.a83776.demo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.di.component.DaggerFragmentComponent;
import com.example.a83776.demo.di.component.FragmentComponent;
import com.example.a83776.demo.di.module.FragmentModule;
import com.example.a83776.demo.util.FixedSpeedScroller;
import com.example.a83776.demo.util.LogUtils;
import com.example.a83776.demo.util.SnackBarUtil;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * description: MVP Fragment基类
 * author: GaoJie
 * created at: 2018/6/25 10:07
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements BaseView {
    private View mView;
    private boolean isInited;
    private Activity mActivity;
    private Context mContext;
    private String TAG;
    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();

    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getClass().getSimpleName();
        TAG = getClass().getSimpleName();
        LogUtils.e(TAG, "onCreate,tag=" + getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initEventAndData();
        super.onViewCreated(view, savedInstanceState);

    }

    protected abstract void initInject();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
        LogUtils.e(TAG, "onDestroyView,tag=" + getTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy,tag=" + getTag());
    }

    public abstract int getLayoutId();

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;

    }

    protected abstract void initEventAndData();

    /**
     * 设置ViewPager滑动速度
     *
     * @param viewPager
     * @param scrollerTime
     */
    public void setScrollerTime(ViewPager viewPager, int scrollerTime) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new DecelerateInterpolator());
            scroller.setTime(scrollerTime);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setDefaultScrollerTime(ViewPager viewpager) {
        setScrollerTime(viewpager, 500);
        LogUtils.d(getClass().getSimpleName(), "设置" + viewpager.getId() + "的滑动速度500");
    }

    @Override
    public void showErrorMsg(String msg) {
        try {
            SnackBarUtil.showLong(((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0), msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }
}
