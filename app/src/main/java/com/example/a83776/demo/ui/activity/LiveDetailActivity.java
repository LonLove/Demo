package com.example.a83776.demo.ui.activity;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.a83776.demo.R;
import com.example.a83776.demo.component.ImageLoader;
import com.example.a83776.demo.contract.LiveCourseDetailContract;
import com.example.a83776.demo.model.bean.JiaZhangKeBiaoListBean;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.presenter.LiveCourseDetailPresenter;
import com.example.a83776.demo.ui.fragment.ConfirmDialogFragment;
import com.example.a83776.demo.util.ToastUtil;
import com.example.a83776.demo.widget.nicevideoplayer.NiceVideoPlayer;
import com.example.a83776.demo.widget.nicevideoplayer.TxVideoPlayerController;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/28 14:23
 */
public class LiveDetailActivity extends CompatHomeKeyActivity1p<LiveCourseDetailPresenter> implements LiveCourseDetailContract.View {
    public static final String ARG_COURSE_ID = "arg_course_id";
    public static final String ARG_ERP_CLASS_ID = "arg_erp_class_id";
    @BindView(R.id.video_view)
    NiceVideoPlayer mVideoView;
    @BindView(R.id.hud_view)
    TableLayout mHudView;
    @BindView(R.id.view_click)
    View mViewClick;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.play)
    Button mPlay;
    @BindView(R.id.lr_left)
    RelativeLayout mLrLeft;
    @BindView(R.id.image)
    ImageView mImage;
    private String mCourseId;
    private String mClassId;
    private LiveDetail mDetail;
    private String mErpDaKeBiaoKeCiUuid;
    private int mTeacherRuankoUserId;
    private LiveRoomInfo mLiveRoomInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mClassId = getIntent().getStringExtra(ARG_ERP_CLASS_ID);
        mCourseId = getIntent().getStringExtra(ARG_COURSE_ID);
        mPresenter.liveDetail(mCourseId);
    }

    //直播课详情回调
    @Override
    public void onLiveDetailResult(int code, String message, LiveDetail lives) {//没有正在直播时为-1，有直播时为课次下标，//是否是假直播 1是假直播
        if (code == 1) {
            LiveDetail detail = lives;
            mDetail = detail;
            if (mDetail.getIsHaveLive() != -1) {//在直播
                mErpDaKeBiaoKeCiUuid = mDetail.getClassScheduleList().get(mDetail.getIsHaveLive()).getErpDaKeBiaoKeCiUuid();
            }
            mTeacherRuankoUserId = mDetail.getClassScheduleList().get(0).getTeacherRuankoUserId();
            if (mDetail.getIsHaveLive() != -1 && mDetail.getIsFalseLive() != 1) {//正在直播
                mPresenter.getScheduleLiveRoomInfo(mErpDaKeBiaoKeCiUuid, String.valueOf(mTeacherRuankoUserId));
            } else if (!TextUtils.isEmpty(mDetail.getAuditionClassVideo())) {//没有在直播，有试听课就播放试听课
                player(mDetail.getAuditionClassVideo(), 0, mDetail.getAuditionClassTitle());
            }
            bindData(mDetail);
        }
    }

    private void bindData(LiveDetail detail) {
        if (detail.getClassScheduleList() == null || detail.getClassScheduleList().isEmpty()) {
            return;
        }
        //顺序：正在直播--宣传片--试听课--课程封面
        int isHaveLive = detail.getIsHaveLive();
        if (isHaveLive == -1 || (mDetail.getIsHaveLive() != -1 && mDetail.getIsFalseLive() == 1)) {//无直播或者假制播
            String videoUrl = detail.getVideoUrl();//宣传片
            String auditionClassVideo = detail.getAuditionClassVideo();//试听课视频
            if (!TextUtils.isEmpty(videoUrl)) {//宣传片
                player(videoUrl, 0, null);

            } else if (!TextUtils.isEmpty(auditionClassVideo)) {//试听课视频
                player(auditionClassVideo, 0, null);
            } else {//图片
                ImageLoader.load(this, detail.getPicture(), mImage, R.drawable.icon_normal_default);
                mImage.setVisibility(View.VISIBLE);
                mVideoView.setVisibility(View.GONE);
            }
        } else {//有直播
            mImage.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
        }
    }

    //播放
    private void player(String url, int seekTo, String title) {
        mVideoView.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        mVideoView.setUp(url, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle(title);
        mVideoView.setController(controller);
        mVideoView.start();
        controller.setOnProgress(new TxVideoPlayerController.OnVideoProgress() {//播放进度
            @Override
            public void onProgress(long duration) {
                if (duration / (1000 * 60) >= 5) {//只能观看5分钟
                    mVideoView.pause();
                }
            }
        });
    }

    //获取直播房间信息回调
    @Override
    public void onScheduleLiveRoomInfoResult(int code, String message, LiveRoomInfo response) {//直播平台：1-声网，0-网易
        mLiveRoomInfo = response;
        if (code == 1) {
            if (response != null && !TextUtils.isEmpty(response.getRtmpPullUrl())) {
                if (response.getZhiBoPingTai() == 0 && response.getZhiBoZhuangTai() == 1) {//只有网易才可以播放直播
                    mViewClick.setVisibility(View.VISIBLE);
                    mImage.setVisibility(View.GONE);
                    mPlay.setVisibility(View.GONE);
                    if (mVideoView.isPlaying() || mVideoView.isPaused() || mVideoView.isError()) {
                        mVideoView.release();
                    }
                    player(response.getRtmpPullUrl(), 0, response.getLiveChannelName());
                } else {
                    if (mDetail != null && !TextUtils.isEmpty(mDetail.getPicture())) {
                        ImageLoader.load(this, mDetail.getPicture(), mImage, R.drawable.icon_normal_default);
                        mImage.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (mDetail != null && !TextUtils.isEmpty(mDetail.getPicture())) {
                    ImageLoader.load(this, mDetail.getPicture(), mImage, R.drawable.icon_normal_default);
                    mImage.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @OnClick(R.id.video_view)
    void onVideoViewClick(View view) {
        //顺序：正在直播--宣传片--试听课--课程封面
        int isHaveLive = mDetail.getIsHaveLive();//没有正在直播时为-1，有直播时为课次下标，//是否是假直播 1是假直播
        if (isHaveLive == -1) {
            //没有播放则播放，有播放则全屏
            if (mVideoView.isIdle() && !mVideoView.isPlaying()) {
                mVideoView.start();
            } else if (mVideoView.isPlaying()) {
                mVideoView.enterFullScreen();
            } else if (mVideoView.isCompleted() || mVideoView.isError()) {//播放完成或者播放错误，点击重新播放
                mVideoView.restart();
            }
        } else {//跳转直播间
            skipLivingRoom();
        }
    }

    @OnClick({R.id.play, R.id.image})
    void onLiveClick(View view) {
        if (mDetail == null) {
            return;
        }
        //顺序：正在直播--宣传片--试听课--课程封面
        int isHaveLive = mDetail.getIsHaveLive();//没有正在直播时为-1，有直播时为课次下标，//是否是假直播 1是假直播
        if (isHaveLive != -1 && mDetail.getIsFalseLive() != 1) {
            skipLivingRoom();
        }
    }

    @OnClick(R.id.view_click)
    void onSkipLivingRoom(View view) {
        skipLivingRoom();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mVideoView != null && (mVideoView.isPaused() || mVideoView.isBufferingPaused())) {
            if (mVideoView.getDataSource().startsWith("rtmp")) {//rtmp流重新打开
                mVideoView.release();
                mVideoView.start();
            } else {
                mVideoView.restart();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (mVideoView != null) {
            if (mVideoView.isFullScreen()) {
                if (mVideoView.exitFullScreen()) {
                    return;
                }
            } else if (mVideoView.isTinyWindow()) {
                if (mVideoView.exitTinyWindow()) {
                    return;
                }
            }
            mVideoView.releasePlayer();
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void skipLivingRoom() {
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BROADCAST_STICKY, Manifest.permission.WAKE_LOCK, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {//所有权限开启才为true，否则为false
                            if (mLiveRoomInfo == null) {
                                return;
                            }
                            JiaZhangKeBiaoListBean kebiao = new JiaZhangKeBiaoListBean();
                            kebiao.setBanJiMingCheng(mDetail.getClassName());
                            kebiao.setErpDaKeBiaoKeCiUuid(mErpDaKeBiaoKeCiUuid);
                            kebiao.setLaoShiRuanKoId(mTeacherRuankoUserId);
                            kebiao.setLaoShiXingMing(mDetail.getClassScheduleList().get(0).getTeacherName());
                            kebiao.setKeChengMingCheng(mDetail.getMarketCourseName());
                            kebiao.setKeChengShouJia(mDetail.getMoneyCount());
                            if (mLiveRoomInfo.getZhiBoZhuangTai() == 1) {//直播平台：1-声网，0-网易
                                if (mLiveRoomInfo.getZhiBoPingTai() == 0) {
                                    LiveRoomActivity.startNetEase(mContext, kebiao, mLiveRoomInfo, mDetail);
                                } else {

                                }
                            } else if (mLiveRoomInfo.getZhiBoZhuangTai() == 2) {
                                ConfirmDialogFragment.newInstance("老师切换平台中，请稍后重试").show(getFragmentManager(), ConfirmDialogFragment.class.getSimpleName());
                            } else if (mLiveRoomInfo.getZhiBoZhuangTai() == 3) {
                                ConfirmDialogFragment.newInstance("该课程已结束").show(getFragmentManager(), ConfirmDialogFragment.class.getSimpleName());
                            } else {
                                ToastUtil.shortShow("老师未直播");
                            }
                        } else {
                            ToastUtil.shortShow("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                        }
                    }
                });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

}
