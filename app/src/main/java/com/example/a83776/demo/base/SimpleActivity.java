package com.example.a83776.demo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

import com.example.a83776.demo.util.FixedSpeedScroller;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * description: 无MVP的activity基类
 * author: GaoJie
 * created at: 2018/6/30 10:44
*/

public abstract class SimpleActivity extends SupportActivity {

    protected Activity mContext;
    private String mPageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageName = this.getClass().getSimpleName();
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        initEventAndData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected void onViewCreated() {

    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();

    /**
     * 设置Viewpager滑动速度
     *
     * @param scrollerTime-
     */
    private void setScrollerTime(ViewPager viewpager, int scrollerTime) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewpager.getContext(), new DecelerateInterpolator());
            scroller.setTime(scrollerTime);
            mScroller.set(viewpager, scroller);
        } catch (Exception e) {
            //ToastUtil.shortShow(e.getMessage());
            e.printStackTrace();
        }
    }

    protected void setDefaultScrollerTime(ViewPager viewpager) {
            setScrollerTime(viewpager, 500);
            Log.d(getClass().getSimpleName(), "设置" + viewpager.getId() + "的滑动速度500");
    }
}
