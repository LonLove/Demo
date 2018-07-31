package com.example.a83776.demo.util;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/25 11:22
 */
public class FixedSpeedScroller extends Scroller {

    private int mDuration=500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public void setTime(int scrollerTime) {
        mDuration = scrollerTime;
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
