package com.example.a83776.demo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.a83776.demo.R;
import com.example.a83776.demo.base.BaseActivity1;
import com.example.a83776.demo.base.BaseNetEaseFragment;
import com.example.a83776.demo.contract.LiveCourseDetailContract;
import com.example.a83776.demo.model.bean.JiaZhangKeBiaoListBean;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.presenter.LiveCourseDetailPresenter;
import com.example.a83776.demo.ui.fragment.LiveNetEaseFragment121;
import com.example.a83776.demo.ui.fragment.LiveNetEaseFragment12n;

import butterknife.BindView;

/**
 * description: 2.0集成声望+网易直播，并拆分为两个fragment
 * author: GaoJie
 * created at: 2018/7/3 11:10
 */
public class LiveRoomActivity extends BaseActivity1<LiveCourseDetailPresenter> implements LiveCourseDetailContract.View,BaseNetEaseFragment.onLiveNeteaseCallBack {
    public static final String EXTRA_CHANNEL_LIVE_ROOM_INFO = "EXTRA_CHANNEL_LIVE_ROOM_INFO";
    public static final String EXTRA_CHANNEL_LIVE_INFO = "EXTRA_CHANNEL_LIVE_INFO";
    public static final String EXTRA_VISITOR_LIVE_COURSE_DETAIL = "EXTRA_VISITOR_LIVE_COURSE_DETAIL";//直播课详情
    public static final String EXTRA_VISITOR_LIMIT_TIME = "EXTRA_VISITOR_LIMIT_TIME";//剩余预览时间
    private static final int TOTAL_TIME = 5*60*1000;//最多观看5分钟
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.content)
    RelativeLayout mContent;
    static Bundle mBundle = null;
    private static long sLastTime;
    private Fragment mCurrentFragment;


    public static void startNetEase(Context context, JiaZhangKeBiaoListBean liveInfo, LiveRoomInfo roomInfo, LiveDetail detail) {
        mBundle = new Bundle();
        mBundle.putInt("SDKTYPE", 2);
        mBundle.putSerializable(EXTRA_CHANNEL_LIVE_ROOM_INFO,roomInfo);
        mBundle.putSerializable(EXTRA_CHANNEL_LIVE_INFO,liveInfo);
        mBundle.putParcelable(EXTRA_VISITOR_LIVE_COURSE_DETAIL,detail);
        boolean isPreviewNodel = liveInfo.getIsHaveQuanXian() != 1;//1是有权限
        boolean isFree = liveInfo.getKeChengShouJia() == 0;
        if (isPreviewNodel&&!isFree) {//没有权限并且是付费的最多观看5分钟
            mBundle.putInt(EXTRA_VISITOR_LIMIT_TIME,TOTAL_TIME);
        }
        Intent intent=new Intent();
        intent.setClass(context, LiveRoomActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
        sLastTime = System.currentTimeMillis();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void initEventAndData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (mBundle != null) {
            if (mBundle.getInt("SDKTYPE") == 1) {

            } else {
                LiveRoomInfo liveRoomInfo = (LiveRoomInfo) mBundle.getSerializable(LiveRoomActivity.EXTRA_CHANNEL_LIVE_ROOM_INFO);
                if (liveRoomInfo != null) {
                    Integer pinKeLeiXing  = liveRoomInfo.getPinKeLeiXing() == null ? 0 : liveRoomInfo.getPinKeLeiXing();
                    liveRoomInfo.setPinKeLeiXing(pinKeLeiXing);//拼课类型（0：一对一，1：小组课，2：多课强化班，3：影课，4：托管）
                    if (pinKeLeiXing == 0) {
                        FragmentManager fm = getSupportFragmentManager();
                        LiveNetEaseFragment121 fragment121 = LiveNetEaseFragment121.newInstance(mBundle);
                        fm.beginTransaction().replace(R.id.fragment_container, fragment121).commit();
                        mCurrentFragment = fragment121;
                    } else {
                        FragmentManager fm = getSupportFragmentManager();
                        LiveNetEaseFragment12n fragment12n = LiveNetEaseFragment12n.newInstance(mBundle);
                        fm.beginTransaction().replace(R.id.fragment_container, fragment12n).commit();
                        mCurrentFragment = fragment12n;
                    }
                }
            }
        }
    }


    @Override
    protected void initInject() {

    }


    @Override
    public void onLiveDetailResult(int code, String message, LiveDetail lives) {

    }

    @Override
    public void onScheduleLiveRoomInfoResult(int code, String message, LiveRoomInfo response) {

    }

    @Override
    public void showErrorMsg(String msg) {

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

    @Override
    public void onLiveTeacherExit() {

    }
}
