package com.example.a83776.netease.entertainment.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.a83776.demo.R;
import com.example.a83776.netease.DemoCache;
import com.example.a83776.netease.base.ui.TActivity;
import com.example.a83776.netease.base.util.log.LogUtil;
import com.example.a83776.netease.entertainment.adapter.GiftAdapter;
import com.example.a83776.netease.entertainment.constant.GiftType;
import com.example.a83776.netease.entertainment.constant.LiveType;
import com.example.a83776.netease.entertainment.constant.PushLinkConstant;
import com.example.a83776.netease.entertainment.constant.PushMicNotificationType;
import com.example.a83776.netease.entertainment.helper.ChatRoomMemberCache;
import com.example.a83776.netease.entertainment.helper.GiftAnimation;
import com.example.a83776.netease.entertainment.module.ChatRoomMsgListPanel;
import com.example.a83776.netease.entertainment.module.ConnectedAttachment;
import com.example.a83776.netease.entertainment.module.DisconnectAttachment;
import com.example.a83776.netease.entertainment.module.GiftAttachment;
import com.example.a83776.netease.entertainment.module.LikeAttachment;
import com.example.a83776.netease.im.session.Container;
import com.example.a83776.netease.im.session.ModuleProxy;
import com.example.a83776.netease.im.session.actions.BaseAction;
import com.example.a83776.netease.im.session.emoji.MoonUtil;
import com.example.a83776.netease.im.session.input.InputConfig;
import com.example.a83776.netease.im.session.input.InputPanel;
import com.example.a83776.netease.im.ui.dialog.DialogMaker;
import com.example.a83776.netease.im.ui.dialog.EasyAlertDialogHelper;
import com.example.a83776.netease.im.ui.periscope.PeriscopeLayout;
import com.example.a83776.netease.permission.MPermission;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.NotificationType;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 直播端和观众端的基类
 * Created by hzxuwen on 2016/4/5.
 */
public abstract class LivePlayerBaseActivity extends TActivity implements ModuleProxy, AVChatStateObserver {
    private static final String TAG = LivePlayerBaseActivity.class.getSimpleName();

    protected final int LIVE_PERMISSION_REQUEST_CODE = 100;
    protected final static String EXTRA_MEETING_NAME = "EXTRA_MEETING_NAME";
    protected final static String EXTRA_ROOM_ID = "ROOM_ID";
    protected final static String EXTRA_URL = "EXTRA_URL";
    protected final static String EXTRA_MODE = "EXTRA_MODE";
    protected final static String EXTRA_CREATOR = "EXTRA_CREATOR";
    private final static int FETCH_ONLINE_PEOPLE_COUNTS_DELTA = 10 * 1000;
    protected final static String AVATAR_DEFAULT = "avatar_default";

    private Timer timer;

    // 聊天室信息
    protected String roomId;
    protected ChatRoomInfo roomInfo;
    protected String pullUrl; // 拉流地址
    protected String masterNick; // 主播昵称
    protected String meetingName; // 音视频会议房间名称
    protected boolean isCreator; // 是否是主播
    protected int screenOrientation; //屏幕方向

    // modules
    protected InputPanel inputPanel;
    protected ChatRoomMsgListPanel messageListPanel;
    private EditText messageEditText;

    // view
    protected ViewGroup rootView;
    protected TextView masterNameText;
    private TextView inputBtn; //文字模式按钮
    private TextView onlineCountText; // 在线人数view
    protected GridView giftView; // 礼物列表
    private RelativeLayout giftAnimationViewDown; // 礼物动画布局1
    private RelativeLayout giftAnimationViewUp; // 礼物动画布局2
    protected PeriscopeLayout periscopeLayout; // 点赞爱心布局
    protected ImageButton giftBtn; // 礼物按钮
    protected ViewGroup giftLayout; // 礼物布局
    protected LinearLayout controlContainer; // 右下角几个image button布局
    protected ViewGroup roomOwnerLayout; // master名称布局
    protected ViewGroup roomNameLayout; //房间名称
    protected TextView roomName; //房间名
    protected TextView interactionBtn; // 互动按钮
    protected TextView fakeListText; // 占坑用的view，message listview可以浮动上下
    protected RelativeLayout connectionViewLayout; // 连麦画面布局
    protected TextView loadingNameText; // 连麦的观众姓名，等待中的画面
    protected TextView onMicNameText; // 连麦的观众姓名
    protected ViewGroup audienceLoadingLayout; // 连麦观众等待画面
    protected ViewGroup audienceLivingLayout; // 连麦观众正在播放画面
    protected TextView livingBg; // 防止用户关闭权限，没有图像时显示
    protected View connectionViewCloseBtn; // 关闭连麦画面按钮
    protected ViewGroup connectionCloseConfirmLayout; // 连麦关闭确认画面
    private TextView connectionCloseConfirmTipsTv;
    protected TextView connectionCloseConfirm; // 连麦关闭确认
    protected TextView connectionCloseCancel; // 连麦关闭取消
    protected TextView loadingClosingText; // 正在连接/已关闭文案
    protected ViewGroup videoModeBgLayout; // 主播画面视频模式背景
    protected ViewGroup audioModeBgLayout; // 主播画面音频模式背景
    protected ViewGroup audioModeBypassLayout; // 音频模式旁路直播画面
    private ImageButton shareBtn; // 分享按钮

    protected AVChatSurfaceViewRenderer bypassVideoRender; // 旁路直播画面

    // data
    protected GiftAdapter adapter;
    protected GiftAnimation giftAnimation; // 礼物动画
    private AbortableFuture<EnterChatRoomResultData> enterRequest;
    protected String onMicNick;  // 连麦用户昵称

    // calculate keyboard height to listen keyboard collapse event
    private int screenHeight = 0;
    private int keyboardOldHeight = -1;
    private int keyboardNowHeight = -1;

    // state
    protected boolean isOnMic = false; // 是否连上麦
    protected boolean isMeOnMic = false; // 是否我自己连上麦
    protected LiveType liveType; // 直播类型
    protected int style; // 语音/视频连麦类型
    protected boolean isDestroyed = false;

    protected abstract int getActivityLayout(); // activity布局文件

    protected abstract int getLayoutId(); // 根布局资源id

    protected abstract int getControlLayout(); // 控制按钮布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏

        parseIntent();

        // 注册监听
        registerObservers(true);

    }

    protected void parseIntent() {
        isCreator = getIntent().getBooleanExtra(EXTRA_CREATOR, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (messageListPanel != null) {
            messageListPanel.onResume();
        }
    }

    @Override
    public void onBackPressed() {
        if (inputPanel != null && inputPanel.collapse(true)) {
        }

        if (messageListPanel != null && messageListPanel.onBackPressed()) {
        }
    }

    @Override
    protected void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
        registerObservers(false);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        adapter = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(getActivityLayout());
        controlContainer.removeAllViews();
        findViews();
    }

    /***********************
     * 录音摄像头权限申请
     *******************************/

    // 权限控制
    protected static final String[] LIVE_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE};

    protected void requestLivePermission() {
        MPermission.with(this)
                .addRequestCode(LIVE_PERMISSION_REQUEST_CODE)
                .permissions(LIVE_PERMISSIONS)
                .request();
    }

    /***************************
     * 监听
     ****************************/

    private void registerObservers(boolean register) {
        NIMClient.getService(ChatRoomServiceObserver.class).observeReceiveMessage(incomingChatRoomMsg, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeOnlineStatus(onlineStatus, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeKickOutEvent(kickOutObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotification, register);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    Observer<List<ChatRoomMessage>> incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
        @Override
        public void onEvent(List<ChatRoomMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }

            for (ChatRoomMessage message : messages) {
                if (message != null && message.getAttachment() instanceof GiftAttachment) {
                    // 收到礼物消息
                    GiftType type = ((GiftAttachment) message.getAttachment()).getGiftType();
                    updateGiftList(type);
                    giftAnimation.showGiftAnimation(message);
                } else if (message != null && message.getAttachment() instanceof LikeAttachment) {
                    // 收到点赞爱心
                    periscopeLayout.addHeart();
                } else if (message != null && message.getAttachment() instanceof ChatRoomNotificationAttachment) {
                    // 通知类消息
                    ChatRoomNotificationAttachment attachment = (ChatRoomNotificationAttachment) message.getAttachment();
                    if (attachment.getType() == NotificationType.ChatRoomMemberIn) {
                        getLiveMode(attachment.getExtension());
                    } else if (attachment.getType() == NotificationType.ChatRoomInfoUpdated) {
                        onReceiveChatRoomInfoUpdate(attachment.getExtension());
                    }
                } else if (message != null && message.getAttachment() instanceof ConnectedAttachment) {
                    // 观众收到旁路直播连接消息
                    onMicConnectedMsg(message);
                } else if (message != null && message.getAttachment() instanceof DisconnectAttachment) {
                    // 观众收到旁路直播断开消息
                    LogUtil.i(TAG, "disconnect");
                    DisconnectAttachment attachment = (DisconnectAttachment) message.getAttachment();
                    if (!TextUtils.isEmpty(attachment.getAccount()) && attachment.getAccount().equals(roomInfo.getCreator())) {
                        resetConnectionView();
                    } else {
                        onMicDisConnectedMsg(attachment.getAccount());
                    }
                } else {
                    messageListPanel.onIncomingMessage(message);
                }
            }
        }
    };

    Observer<CustomNotification> customNotification = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification customNotification) {
            if (customNotification == null) {
                return;
            }

            String content = customNotification.getContent();
            try {
                JSONObject json = JSON.parseObject(content);
                String fromRoomId = json.getString(PushLinkConstant.roomid);
                if (!roomId.equals(fromRoomId)) {
                    return;
                }
                int id = json.getIntValue(PushLinkConstant.command);
                LogUtil.i(TAG, "receive command type:" + id);
                if (id == PushMicNotificationType.JOIN_QUEUE.getValue()) {
                    // 加入连麦队列
                    joinQueue(customNotification, json);
                } else if (id == PushMicNotificationType.EXIT_QUEUE.getValue()) {
                    // 退出连麦队列
                    exitQueue(customNotification);
                } else if (id == PushMicNotificationType.CONNECTING_MIC.getValue()) {
                    // 主播选中某人连麦
                    onMicLinking(json);
                } else if (id == PushMicNotificationType.DISCONNECT_MIC.getValue()) {
                    // 被主播断开连麦
                    onMicCanceling();
                } else if (id == PushMicNotificationType.REJECT_CONNECTING.getValue()) {
                    // 观众由于重新进入了房间而拒绝连麦
                    rejectConnecting();
                }

            } catch (Exception e) {
                LogUtil.e(TAG, e.toString());
            }
        }
    };

    Observer<ChatRoomStatusChangeData> onlineStatus = new Observer<ChatRoomStatusChangeData>() {
        @Override
        public void onEvent(ChatRoomStatusChangeData chatRoomStatusChangeData) {
            if (chatRoomStatusChangeData.status == StatusCode.CONNECTING) {
                DialogMaker.updateLoadingMessage("连接中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.UNLOGIN) {
                onOnlineStatusChanged(false);
                Toast.makeText(LivePlayerBaseActivity.this, R.string.nim_status_unlogin, Toast.LENGTH_SHORT).show();
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
                DialogMaker.updateLoadingMessage("登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
                onOnlineStatusChanged(true);
            } else if (chatRoomStatusChangeData.status.wontAutoLogin()) {
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
                onOnlineStatusChanged(false);
                Toast.makeText(LivePlayerBaseActivity.this, R.string.net_broken, Toast.LENGTH_SHORT).show();
            }
            LogUtil.i(TAG, "Chat Room Online Status:" + chatRoomStatusChangeData.status.name());
        }
    };

    /**
     * 用户状态变化
     */
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                clearChatRoom();
            }
        }
    };

    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
            Toast.makeText(LivePlayerBaseActivity.this, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), IdentifyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            clearChatRoom();
        }
    };


    /**************************
     * 断网重连
     ****************************/

    protected void onOnlineStatusChanged(boolean isOnline) {
        if (isOnline) {
            onConnected();
        } else {
            onDisconnected();
        }
    }

    protected abstract void onConnected(); // 网络连上

    protected abstract void onDisconnected(); // 网络断开

    /****************************
     * 布局初始化
     **************************/

    protected void findViews() {

        roomName = findView(R.id.room_name);
        masterNameText = findView(R.id.master_name);
        onlineCountText = findView(R.id.online_count_text);
        roomOwnerLayout = findView(R.id.room_owner_layout);
        roomNameLayout = findView(R.id.room_name_layout);

        //控制布局
        findControlViews();

        interactionBtn = findView(R.id.interaction_btn);
        giftBtn = findView(R.id.gift_btn);
        shareBtn = findView(R.id.share_btn);
        shareBtn.setOnClickListener(baseClickListener);
        // 礼物列表
        findGiftLayout();
        // 点赞的爱心布局
        periscopeLayout = (PeriscopeLayout) findViewById(R.id.periscope);

        // 互动连麦布局
        findInteractionViews();

    }

    protected void findControlViews() {
        controlContainer = findView(R.id.control_container);
        View view = LayoutInflater.from(this).inflate(getControlLayout(), null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        controlContainer.addView(view, lp);
    }

    protected void findInputViews() {
        Container container = new Container(this, roomId, SessionTypeEnum.ChatRoom, this);
        View view = findViewById(getLayoutId());
        if (messageListPanel == null) {
            messageListPanel = new ChatRoomMsgListPanel(container, view);
        }
        InputConfig inputConfig = new InputConfig();
        inputConfig.isTextAudioSwitchShow = false;
        inputConfig.isMoreFunctionShow = false;
        inputConfig.isEmojiButtonShow = false;
        if (inputPanel == null) {
            inputPanel = new InputPanel(container, view, getActionList(), inputConfig);
        } else {
            inputPanel.reload(container, inputConfig);
        }
        messageEditText = findView(R.id.editTextMessage);

        inputBtn = findView(R.id.input_btn);
        inputBtn.setOnClickListener(baseClickListener);
        inputPanel.hideInputPanel();
        inputPanel.collapse(true);
    }

    // 初始化礼物布局
    protected void findGiftLayout() {
        giftLayout = findView(R.id.gift_layout);
        giftView = findView(R.id.gift_grid_view);

        giftAnimationViewDown = findView(R.id.gift_animation_view);
        giftAnimationViewUp = findView(R.id.gift_animation_view_up);

        giftAnimation = new GiftAnimation(giftAnimationViewDown, giftAnimationViewUp);
    }

    // 更新礼物列表，由子类定义
    protected void updateGiftList(GiftType type) {

    }

    // 互动连麦布局
    private void findInteractionViews() {
        audioModeBgLayout = findView(R.id.audio_mode_background);
        videoModeBgLayout = findView(R.id.video_layout);
        bypassVideoRender = findView(R.id.bypass_video_render);
        bypassVideoRender.setZOrderMediaOverlay(true);
        showModeLayout();
        connectionViewLayout = findView(R.id.interaction_view_layout);
        loadingNameText = findView(R.id.loading_name);
        onMicNameText = findView(R.id.on_mic_name);
        audienceLoadingLayout = findView(R.id.audience_loading_layout);
        audienceLivingLayout = findView(R.id.audience_living_layout);
        livingBg = findView(R.id.no_video_bg);
        connectionViewCloseBtn = findView(R.id.interaction_close_btn);
        connectionCloseConfirmLayout = findView(R.id.interaction_close_confirm_layout);
        connectionCloseConfirmTipsTv = findView(R.id.interaction_close_confirm_tips_tv);
        connectionCloseConfirm = findView(R.id.close_confirm);
        connectionCloseCancel = findView(R.id.close_cancel);
        loadingClosingText = findView(R.id.loading_closing_text);
        audioModeBypassLayout = findView(R.id.audio_mode_audience_layout);

        connectionViewCloseBtn.setOnClickListener(baseClickListener);
        connectionCloseConfirm.setOnClickListener(baseClickListener);
        connectionCloseCancel.setOnClickListener(baseClickListener);
    }

    // 连麦布局显示
    protected void showModeLayout() {
        if (liveType == LiveType.VIDEO_TYPE) {
            videoModeBgLayout.setVisibility(View.VISIBLE);
            audioModeBgLayout.setVisibility(View.GONE);
        } else if (liveType == LiveType.AUDIO_TYPE) {
            videoModeBgLayout.setVisibility(View.GONE);
            audioModeBgLayout.setVisibility(View.VISIBLE);
        }
    }

    /****************************
     * 进入聊天室
     ***********************/

    // 进入聊天室
    public void enterRoom() {
        if(isDestroyed) {
            return;
        }
        DialogMaker.showProgressDialog(this, null, "", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (enterRequest != null) {
                    enterRequest.abort();
                    onLoginDone();
                    finish();
                }
            }
        }).setCanceledOnTouchOutside(false);
        EnterChatRoomData data = new EnterChatRoomData(roomId);
        setEnterRoomExtension(data);
        enterRequest = NIMClient.getService(ChatRoomService.class).enterChatRoom(data);
        enterRequest.setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                onLoginDone();
                roomInfo = result.getRoomInfo();
                ChatRoomMember member = result.getMember();
                member.setRoomId(roomInfo.getRoomId());
                ChatRoomMemberCache.getInstance().saveMyMember(member);
                Map<String, Object> ext = roomInfo.getExtension();
                getLiveMode(ext);
                updateUI();
                updateRoomUI(false);
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == ResponseCode.RES_CHATROOM_BLACKLIST) {
                    Toast.makeText(LivePlayerBaseActivity.this, "你已被拉入黑名单，不能再进入", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LivePlayerBaseActivity.this, "enter chat room failed, code=" + code, Toast.LENGTH_SHORT).show();
                }
                if (isCreator) {
                    finish();
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                Toast.makeText(LivePlayerBaseActivity.this, "enter chat room exception, e=" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                if (isCreator) {
                    finish();
                }
            }
        });
    }

    // 主播将自己的模式放到进入聊天室的通知扩展中，告诉观众，由主播实现。
    protected void setEnterRoomExtension(EnterChatRoomData enterChatRoomData) {

    }

    // 获取当前直播的模式
    private void getLiveMode(Map<String, Object> ext) {
        if (ext != null) {
            if (ext.containsKey(PushLinkConstant.type)) {
                int type = (int) ext.get(PushLinkConstant.type);
                liveType = LiveType.typeOfValue(type);
            }

            if (ext.containsKey(PushLinkConstant.meetingName)) {
                meetingName = (String) ext.get(PushLinkConstant.meetingName);
            }

            if(ext.containsKey(PushLinkConstant.orientation)) {
                screenOrientation = (int) ext.get(PushLinkConstant.orientation);
                if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                        ? 1 : 2 ) != screenOrientation) {
                    setRequestedOrientation(screenOrientation == 1? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        }
    }

    private void onLoginDone() {
        enterRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    // 更新在线人数
    protected void updateUI() {
        roomName.setText(roomId);
        masterNameText.setText(roomInfo.getCreator());
        onlineCountText.setText(String.format("%s人", String.valueOf(roomInfo.getOnlineUserCount())));
        fetchOnlineCount();
    }

    // 聊天室信息相关界面
    protected void updateRoomUI(boolean isHide) {
        if (isHide) {
            controlContainer.setVisibility(View.GONE);
            roomOwnerLayout.setVisibility(View.GONE);
            roomNameLayout.setVisibility(View.GONE);
        } else {
            controlContainer.setVisibility(View.VISIBLE);
            roomOwnerLayout.setVisibility(View.VISIBLE);
            roomNameLayout.setVisibility(View.VISIBLE);
        }
    }

    // 一分钟轮询一次在线人数
    private void fetchOnlineCount() {
        if (timer == null) {
            timer = new Timer();
        }

        //开始一个定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                NIMClient.getService(ChatRoomService.class).fetchRoomInfo(roomId).setCallback(new RequestCallback<ChatRoomInfo>() {
                    @Override
                    public void onSuccess(final ChatRoomInfo param) {
                        onlineCountText.setText(String.format("%s人", String.valueOf(param.getOnlineUserCount())));
                    }

                    @Override
                    public void onFailed(int code) {
                        LogUtil.d(TAG, "fetch room info failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        LogUtil.d(TAG, "fetch room info exception:" + exception);
                    }
                });
            }
        }, FETCH_ONLINE_PEOPLE_COUNTS_DELTA, FETCH_ONLINE_PEOPLE_COUNTS_DELTA);
    }

    /*******************
     * 离开聊天室
     ***********************/

    private void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 50);
    }


    protected void onReceiveChatRoomInfoUpdate(Map<String, Object> extension) {

    }

    /**************************
     * Module proxy
     ***************************/

    @Override
    public boolean sendMessage(IMMessage msg) {
        ChatRoomMessage message = (ChatRoomMessage) msg;

        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }

        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });
        messageListPanel.onMsgSend(msg);
        return true;
    }

    @Override
    public void onInputPanelExpand() {
        controlContainer.setVisibility(View.GONE);
        if (fakeListText != null) {
            fakeListText.setVisibility(View.GONE);
        }
        if (isOnMic && roomInfo.getCreator().equals(DemoCache.getAccount())) {
            connectionViewLayout.setVisibility(View.GONE);
            bypassVideoRender.setVisibility(View.GONE);
            bypassVideoRender.setZOrderMediaOverlay(false);
        }
    }

    @Override
    public void shouldCollapseInputPanel() {
        inputPanel.collapse(false);
        controlContainer.setVisibility(View.VISIBLE);
        if (fakeListText != null) {
            fakeListText.setVisibility(View.VISIBLE);
        }
        if (isOnMic && roomInfo.getCreator().equals(DemoCache.getAccount())) {
            connectionViewLayout.setVisibility(View.VISIBLE);
            bypassVideoRender.setVisibility(View.VISIBLE);
            bypassVideoRender.setZOrderMediaOverlay(true);
        }
    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        return actions;
    }

    /***********************
     * 连麦相关操作
     *****************************/

    View.OnClickListener baseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.interaction_close_btn:
                    connectionViewCloseBtn.setVisibility(View.GONE);
                    connectionCloseConfirmLayout.setVisibility(View.VISIBLE);
                    if (style == AVChatType.AUDIO.getValue()) {
                        connectionCloseConfirmTipsTv.setText(R.string.interaction_audio_close_title);
                    } else {
                        connectionCloseConfirmTipsTv.setText(R.string.interaction_video_close_title);
                    }
                    break;
                case R.id.close_confirm:
                    doCloseInteraction();
                    doCloseInteractionView();
                    break;
                case R.id.close_cancel:
                    connectionCloseConfirmLayout.setVisibility(View.GONE);
                    connectionViewCloseBtn.setVisibility(View.VISIBLE);
                    break;
                case R.id.input_btn:
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            startInputActivity();
                        }
                    });
                    break;
                case R.id.share_btn:
                    if (pullUrl != null) {
                        ClipboardManager cm = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(pullUrl);
                        EasyAlertDialogHelper.showOneButtonDiolag(LivePlayerBaseActivity.this,
                                R.string.share_address_dialog_title, R.string.share_address_dialog_message,
                                R.string.share_address_dialog_know, false, null);
                    }

                    break;
            }
        }
    };

    /**************************
     * 互动连麦入队/出队操作
     **************************/

    // 加入连麦队列，由主播端实现
    protected void joinQueue(CustomNotification customNotification, JSONObject json) {

    }

    // 退出连麦队列，由主播端实现
    protected void exitQueue(CustomNotification customNotification) {

    }

    // 主播选中某人连麦，由观众实现
    protected void onMicLinking(JSONObject jsonObject) {

    }

    // 观众由于重新进入房间，而拒绝连麦，由主播实现
    protected void rejectConnecting() {

    }

    // 收到连麦成功消息，由观众端实现
    protected void onMicConnectedMsg(ChatRoomMessage message) {
    }

    // 收到取消连麦消息,由观众的实现
    protected void onMicDisConnectedMsg(String account) {
    }

    // 子类继承
    protected void showConnectionView(String account, String nick, int style) {
        isOnMic = true;
        updateOnMicName(nick);
    }

    // 设置连麦者昵称
    protected void updateOnMicName(String nick) {
        LogUtil.d(TAG, "updateOnMicName: " + nick);
        if(nick == null) {
            return;
        }
        onMicNick = nick;
        onMicNameText.setVisibility(View.VISIBLE);
        onMicNameText.setText(String.format("连麦者:%s", nick));
    }

    // 断开连麦,由子类实现
    protected abstract void doCloseInteraction();


    // 主播断开连麦者，由观众实现
    protected void onMicCanceling() {

    }

    protected void doCloseInteractionView() {
        loadingClosingText.setText(style == AVChatType.AUDIO.getValue() ? R.string.audio_closed : R.string.video_closed);
        audienceLoadingLayout.setVisibility(View.VISIBLE);
        loadingNameText.setText(!TextUtils.isEmpty(onMicNick) ? onMicNick : "");
        livingBg.setVisibility(View.GONE);
        connectionViewCloseBtn.setVisibility(View.GONE);
        connectionCloseConfirmLayout.setVisibility(View.GONE);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetConnectionView();
            }
        }, 2000);
    }

    protected void resetConnectionView() {
        isOnMic = false;
        connectionViewLayout.setVisibility(View.GONE);
        connectionCloseConfirmLayout.setVisibility(View.GONE);
        audienceLivingLayout.setVisibility(View.GONE);
        audienceLoadingLayout.setVisibility(View.GONE);
        connectionViewCloseBtn.setVisibility(View.VISIBLE);
        onMicNameText.setVisibility(View.GONE);
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */
    private InputConfig inputConfig = new InputConfig(false, false, false);

    private void startInputActivity() {
        InputActivity.startActivityForResult(this, messageEditText.getText().toString(),
                inputConfig, new InputActivity.InputActivityProxy() {
                    @Override
                    public void onSendMessage(String text) {
                        inputPanel.onTextMessageSendButtonPressed(text);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == InputActivity.REQ_CODE) {
            // 设置EditText显示的内容
            String text = data.getStringExtra(InputActivity.EXTRA_TEXT);
            MoonUtil.identifyFaceExpression(DemoCache.getContext(), messageEditText, text, ImageSpan.ALIGN_BOTTOM);
            messageEditText.setSelection(text.length());
            inputPanel.hideInputPanel();
            // 根据mode显示表情布局或者键盘布局
            int mode = data.getIntExtra(InputActivity.EXTRA_MODE, InputActivity.MODE_KEYBOARD_COLLAPSE);
            if (mode == InputActivity.MODE_SHOW_EMOJI) {
                inputPanel.toggleEmojiLayout();
            } else if (mode == InputActivity.MODE_SHOW_MORE_FUNC) {
                inputPanel.toggleActionPanelLayout();
            }

            //inputPanel.show();
        }
    }
}
