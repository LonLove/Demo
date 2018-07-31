package com.example.a83776.demo.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.badoo.mobile.util.WeakHandler;
import com.example.a83776.demo.R;
import com.example.a83776.demo.app.Constants;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.ui.activity.LiveRoomActivity;
import com.example.a83776.demo.util.PermissionUtils;
import com.example.a83776.demo.util.ToastUtil;
import com.example.a83776.netease.DemoCache;
import com.example.a83776.netease.base.util.NetworkUtil;
import com.example.a83776.netease.base.util.log.LogUtil;
import com.example.a83776.netease.base.util.string.MD5;
import com.example.a83776.netease.entertainment.constant.LiveType;
import com.example.a83776.netease.entertainment.constant.PushLinkConstant;
import com.example.a83776.netease.entertainment.constant.PushMicNotificationType;
import com.example.a83776.netease.entertainment.helper.ChatRoomMemberCache;
import com.example.a83776.netease.entertainment.module.ConnectedAttachment;
import com.example.a83776.netease.entertainment.module.DisconnectAttachment;
import com.example.a83776.netease.entertainment.module.GiftAttachment;
import com.example.a83776.netease.entertainment.module.LikeAttachment;
import com.example.a83776.netease.im.business.RegisterHttpClient;
import com.example.a83776.netease.im.config.AuthPreferences;
import com.example.a83776.netease.im.config.UserPreferences;
import com.example.a83776.netease.im.session.ModuleProxy;
import com.example.a83776.netease.im.session.input.InputConfig;
import com.example.a83776.netease.im.ui.dialog.DialogMaker;
import com.example.a83776.netease.permission.MPermission;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
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
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * description:
 * author: GaoJie
 * created at: 2018/7/5 13:55
 */
public abstract class BaseNetEaseFragment extends BaseFragment implements ModuleProxy, AVChatStateObserver {
    protected static final String TAG = BaseNetEaseFragment.class.getSimpleName();
    protected static Handler handler;
    protected LiveType liveType;// 直播类型
    //聊天室信息
    protected ChatRoomInfo mChatRoomInfo;//直播房间信息
    protected String mRoomId;//聊天室id
    protected String mChannelName;//音视频会议房间名称
    protected String mChannelId;//音视频会议房间ID
    protected String mPullUrl;//推流/拉流地址
    protected Integer mPinKeLeiXing = 0;//拼课类型(0：一对一，1：小组课，2：多科强化班，3：影课，4：托管)
    protected boolean isCreator;//是否是主播
    protected boolean isOnMic;
    protected Activity mActivity;
    protected AbortableFuture<EnterChatRoomResultData> enterRequest;
    protected Timer timer;
    protected final static int FETCH_ONLINE_PEOPLE_COUNTS_DELTA = 2 * 1000;
    protected final int LIVE_PERMISSION_REQUEST_CODE = 100;
    public String loginUserId;
    private AbortableFuture<LoginInfo> loginRequest;
    private WeakHandler mWeakHandler;
    private onLiveNeteaseCallBack mListener;

    public interface onLiveNeteaseCallBack {
        void onLiveTeacherExit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
        if (mActivity instanceof onLiveNeteaseCallBack) {
            mListener = ((onLiveNeteaseCallBack) mActivity);
        } else {
            throw new RuntimeException(mActivity.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        int arg = random.nextInt(999999) + 111111;
        loginUserId = arg + "";
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        if (!NetworkUtil.isNetAvailable(mActivity)) {
            onNetWorkUnAvailable();
        }
        parseIntent();
        // 注册监听
        registerObservers(true);
        mWeakHandler = new WeakHandler();

    }

    /**
     * 网络不可用
     */
    protected void onNetWorkUnAvailable() {
        Log.d(TAG, "网络不可用");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mWeakHandler != null) {
            mWeakHandler.removeCallbacksAndMessages(null);
        }
    }

    private void parseIntent() {
        Bundle arguments = getArguments();
        LiveRoomInfo mRoomInfo = (LiveRoomInfo) arguments.getSerializable(LiveRoomActivity.EXTRA_CHANNEL_LIVE_ROOM_INFO);
        mRoomId = mRoomInfo.getRoomId();
        mChannelName = mRoomInfo.getLiveChannelName();
        mChannelId = mRoomInfo.getChannelId();
        mPullUrl = mRoomInfo.getRtmpPullUrl();
        mPinKeLeiXing = mRoomInfo.getPinKeLeiXing();
        isCreator = false;
        Log.i(TAG, "mRoomId=" + mRoomId + ",mChannelName=" + mChannelName + ",mChannelId=" + mChannelId + ",mPullUrl=" + mPullUrl + ",mPinKeLeiXing=" + mPinKeLeiXing);
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

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestMultiSDPermissions(mActivity, mPermissionGrant);
        }
    }

    PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    onPermissionNoGranted_();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPermissionNoGranted(int requestCode) {
            Toast.makeText(mActivity, "未授权", Toast.LENGTH_LONG).show();
        }
    };

    protected abstract void onPermissionNoGranted_();


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
                  /*  // 收到礼物消息
                    GiftType type = ((GiftAttachment) message.getAttachment()).getGiftType();
                    updateGiftList(type);
                    giftAnimation.showGiftAnimation(message);*/
                } else if (message != null && message.getAttachment() instanceof LikeAttachment) {
                  /*  // 收到点赞爱心
                    periscopeLayout.addHeart();*/
                } else if (message != null && message.getAttachment() instanceof ChatRoomNotificationAttachment) {
                    // 通知类消息
                    ChatRoomNotificationAttachment attachment = (ChatRoomNotificationAttachment) message.getAttachment();
                    if (attachment.getType() == NotificationType.ChatRoomMemberIn) {//成员进入聊天室
                        getLiveMode(attachment.getExtension());
                    } else if (attachment.getType() == NotificationType.ChatRoomMemberExit) {//成员离开聊天室
                        if (attachment.getOperator().equals(mChatRoomInfo.getCreator())) {
                            onTeacherExitChatRoom();
                        } else {
                            onOtherExitChatRoom();
                        }
                    } else if (attachment.getType() == NotificationType.ChatRoomInfoUpdated) {//聊天室信息被更新了
                        onReceiveChatRoomInfoUpdate(attachment.getExtension());
                    }
                } else if (message != null && message.getAttachment() instanceof ConnectedAttachment) {
                    // 观众收到旁路直播连接消息
                    onMicConnectedMsg(message);
                } else if (message != null && message.getAttachment() instanceof DisconnectAttachment) {
                    // 观众收到旁路直播断开消息
                    LogUtil.i(TAG, "disconnect");
                    DisconnectAttachment attachment = (DisconnectAttachment) message.getAttachment();
                    if (!TextUtils.isEmpty(attachment.getAccount()) && attachment.getAccount().equals(mChatRoomInfo.getCreator())) {
                        resetConnectionView();
                    } else {
                        onMicDisConnectedMsg(attachment.getAccount());
                    }
                } else {
                    //                    messageListPanel.onIncomingMessage(message);
                }
            }
        }
    };


    // 收到连麦成功消息，由观众端实现
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
                if (!mRoomId.equals(fromRoomId)) {
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
                Toast.makeText(mActivity, R.string.nim_status_unlogin, Toast.LENGTH_SHORT).show();
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
                //                DialogMaker.updateLoadingMessage("登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
                onOnlineStatusChanged(true);
            } else if (chatRoomStatusChangeData.status.wontAutoLogin()) {
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
                onOnlineStatusChanged(false);
                Toast.makeText(mActivity, R.string.net_broken, Toast.LENGTH_SHORT).show();
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
            LogUtil.i(TAG, "用户状态变化：code=" + code);
            if (code == StatusCode.KICKOUT) {//其他设备登录成功
                kickOutByOtherClient();
                LogUtil.i(TAG, "用户状态变化：code.getValue()=" + code.getValue());
            } else if (code.wontAutoLogin()) {
                clearChatRoom();
            }
        }
    };

    /**
     * 其他设备成功登录
     */
    private void kickOutByOtherClient() {
        ToastUtil.shortShow("该账户已在别处登陆");
        registerObservers(false);
        String log = "正在退出....";
        Log.e(TAG, log);

        log = "清空聊天室成员资料缓存.";
        Log.e(TAG, log);
        ChatRoomMemberCache.getInstance().clear();

        log = "退出网易.";
        NIMClient.getService(AuthService.class).logout();
        Log.e(TAG, log);
    }

    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
         /*   Toast.makeText(mActivity, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), IdentifyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            clearChatRoom();*/
            LogUtil.i(TAG, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason());
        }
    };

    // 获取当前直播的模式
    private void getLiveMode(Map<String, Object> ext) {
        if (ext != null) {
            if (ext.containsKey(PushLinkConstant.type)) {
                int type = (int) ext.get(PushLinkConstant.type);
                liveType = LiveType.typeOfValue(type);
            }
            if (ext.containsKey(PushLinkConstant.meetingName)) {
                mChannelName = (String) ext.get(PushLinkConstant.meetingName);
            }

        }
    }

    /**
     * 房间内用户离开通知
     */
    protected abstract void onOtherExitChatRoom();

    /**
     * 老师离开聊天室
     */
    protected void onTeacherExitChatRoom() {

    }

    protected void onReceiveChatRoomInfoUpdate(Map<String, Object> extension) {

    }

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

    protected void resetConnectionView() {
    }

    // 子类继承
    protected void showConnectionView(String account, String nick, int style) {
        isOnMic = true;
        updateOnMicName(nick);
    }

    // 设置连麦者昵称
    protected void updateOnMicName(String nick) {
    }

    // 断开连麦,由子类实现
    protected abstract void doCloseInteraction();


    // 主播断开连麦者，由观众实现
    protected void onMicCanceling() {

    }

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

    /**
     * 网络连上
     */
    protected abstract void onConnected();

    /**
     * 网络断开
     */
    protected abstract void onDisconnected();

    /*******************
     * 离开聊天室
     ***********************/

    private void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(mRoomId);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivity.finish();
            }
        }, 50);
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    /****************************
     * 进入聊天室
     ***********************/

    // 进入聊天室
    public void enterRoom() {
      /*  DialogMaker.showProgressDialog(this, null, "", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (enterRequest != null) {
                    enterRequest.abort();
                    onLoginDone();
                    finish();
                }
            }
        }).setCanceledOnTouchOutside(false);*/
        EnterChatRoomData data = new EnterChatRoomData(mRoomId);
        setEnterRoomExtension(data);
        enterRequest = NIMClient.getService(ChatRoomService.class).enterChatRoom(data);
        enterRequest.setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                onLoginDone();
                mChatRoomInfo = result.getRoomInfo();
                ChatRoomMember member = result.getMember();
                member.setRoomId(mChatRoomInfo.getRoomId());
                ChatRoomMemberCache.getInstance().saveMyMember(member);
                Map<String, Object> ext = mChatRoomInfo.getExtension();
                getLiveMode(ext);
                updateUI();
                updateRoomUI(false);
            }


            @Override
            public void onFailed(int code) {
                //                onLoginDone();
                if (code == ResponseCode.RES_CHATROOM_BLACKLIST) {
                    Toast.makeText(mActivity, "你已被拉入黑名单，不能再进入", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "enter chat room failed, code=" + code, Toast.LENGTH_SHORT).show();
                }
                if (isCreator) {
                    mActivity.finish();
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                Toast.makeText(mActivity, "enter chat room exception, e=" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                if (isCreator) {
                    mActivity.finish();
                }
            }
        });
    }

    // 主播将自己的模式放到进入聊天室的通知扩展中，告诉观众，由主播实现。
    protected void setEnterRoomExtension(EnterChatRoomData enterChatRoomData) {

    }

    private void onLoginDone() {
        enterRequest = null;
    }

    // 更新在线人数
    protected void updateUI() {
        onChannelName(mChatRoomInfo.getOnlineUserCount());
        fetchOnlineCount();
    }

    /**
     * 更新在线人数
     *
     * @param num
     */
    protected abstract void onChannelName(int num);

    // 观众端进入聊天室成功，显示聊天室信息相关界面
    protected void updateRoomUI(boolean isHide) {

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
                NIMClient.getService(ChatRoomService.class).fetchRoomInfo(mRoomId).setCallback(new RequestCallback<ChatRoomInfo>() {
                    @Override
                    public void onSuccess(final ChatRoomInfo param) {
                        onChannelName(param.getOnlineUserCount());
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

    /**************************
     * Module proxy
     ***************************/

    @Override
    public boolean sendMessage(IMMessage msg) {
        final ChatRoomMessage message = (ChatRoomMessage) msg;
        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(mRoomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }
        final String account;
        account = loginUserId;
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        Toast.makeText(DemoCache.getContext(), "sendMessage onSuccess", Toast.LENGTH_SHORT).show();
                        String log = "[消息发送]:回调 成功(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",message=" + message.getContent() + ")";
                        Log.e(TAG, log);
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                            String log = "[消息发送]:回调 Failed 用户被禁言(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",error=" + code + ")";
                            Log.e(TAG, log);
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                        String log = "[消息发送]:回调 Exception (ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",e=" + exception.getMessage() + ")";
                        Log.e(TAG, log);
                    }
                });
        return true;
    }

    @Override
    public boolean sendMessage(IMMessage msg, final String messageId) {
        final ChatRoomMessage message = (ChatRoomMessage) msg;
        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(mRoomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }
        final String account;
        account = loginUserId;
        String log = "[消息发送]:发送中...(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",message=" + message.getContent() + ")";
        Log.e(TAG, log);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        removeMessage(messageId);
                        String log = "[消息发送]:回调 成功(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",message=" + message.getContent() + ")";
                        Log.e(TAG, log);

                    }

                    @Override
                    public void onFailed(int code) {
                        onMessageSendFailed(messageId, code);
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                            String log = "[消息发送]:回调 Failed 用户被禁言(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",error=" + code + ")";
                            Log.e(TAG, log);
                        } else {
                            reSendMessage(messageId);
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                            String log = "[消息发送]:回调 Failed 消息发送失败：code:(ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",error=" + code + ")";
                            Log.e(TAG, log);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        onMessageSendException(messageId, exception);
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                        String log = "[消息发送]:回调 Exception (ruankoId=" + account + ",roomId=" + mRoomId + ",channelName=" + mChannelName + ",channelId=" + mChannelId + ",e=" + exception.getMessage() + ")";
                        Log.e(TAG, log);
                    }
                });
        return true;
    }

    /**
     * 消息发送异常
     *
     * @param messageId
     * @param exception
     */
    private void onMessageSendException(String messageId, Throwable exception) {

    }

    /**
     * 重发消息
     *
     * @param messageId
     */
    private void reSendMessage(String messageId) {
        String message = mMessagePool.get(messageId);
        if (message != null) {
            sendChannelMessage(message, messageId);
        }
    }

    /**
     * 发送消息
     *
     * @param message
     * @param messageId
     */
    private void sendChannelMessage(String message, String messageId) {
        if (messageId == null) {
            messageId = createMessageId();
            putMessage(message, messageId);
        }
        IMMessage textMessage;
        textMessage = ChatRoomMessageBuilder.createChatRoomTextMessage(mRoomId, message);
        sendMessage(textMessage, messageId);
    }

    /**
     * 创建消息Id
     *
     * @return 时间（精确到毫秒）——用户Id
     */
    private String createMessageId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date()) + loginUserId;
    }

    /**
     * 消息发送失败
     *
     * @param messageId
     * @param code
     */
    private void onMessageSendFailed(String messageId, int code) {

    }

    private HashMap<String, String> mMessagePool = new HashMap<>();

    /**
     * 将消息放到Map中，成功了就删除，失败了就重发
     *
     * @param messageId
     * @param message
     */
    public void putMessage(String messageId, String message) {
        mMessagePool.put(messageId, message);
    }

    private void removeMessage(String messageId) {
        mMessagePool.remove(messageId);
    }

    protected void register() {
        // 注册流程
        final String account;
        //        account = loginUserId + Constants.NETEASE_ROLE;
        account = "" + 100207631;
//        final String pwdNet = tokenFromPassword(Constants.NETEASE_PWD);
        final String nickName = account;
        final String password = Constants.NETEASE_PWD;
        String avatar = null;
        avatar = "https://test";
        RegisterHttpClient.getInstance().register(account, nickName, password,avatar, new RegisterHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String log = "[注册NIM]:注册成功(account=" + account + ", nickName=" + nickName + ")";
                Log.e(TAG, log);
                login();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                if (code == 414) {//账号已被注册
                    String log = "[注册NIM]:账号已被注册(account=" + account + ", nickName=" + nickName + ")";
                    Log.e(TAG, log);
                    login();
                } else {
                    String log = "[注册NIM]:注册失败(account=" + account + ", nickName=" + nickName + ", errorMsg=" + errorMsg + ")";
                    Log.e(TAG, log);
                }
            }
        });
    }

    protected void login() {
        // 登录流程
        final String account = 100207631+ "";
        final String pwdNet = Constants.NETEASE_PWD;
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, pwdNet));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.i(TAG, "login success");
                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, pwdNet);
                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
                // 初始化免打扰
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());
                String log = "[登录NIM]:登录成功(account=" + account + ")";
                Log.e(TAG, log);
                enterRoom();
            }

            @Override
            public void onFailed(int code) {
                onLoginFailed();
                if (code == 404) {
                    Toast.makeText(mActivity, "聊天室不存在", Toast.LENGTH_SHORT).show();
                    //
                    String log = "[登录NIM]:登录失败(account=" + account + ", token=" + pwdNet + ", 聊天室不存在)";
                    Log.e(TAG, log);
                    //
                    ChatRoomMemberCache.getInstance().clear();
                    NIMClient.getService(AuthService.class).logout();
                } else if (code == 302) {
                    Toast.makeText(mActivity, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    //
                    String log = "[登录NIM]:登录失败(account=" + account + ", token=" + pwdNet + ", 帐号或密码错误)";
                    Log.e(TAG, log);
                    //
                    ChatRoomMemberCache.getInstance().clear();
                    NIMClient.getService(AuthService.class).logout();
                    //修改账号密码
                    //updateToken(account, Constants.NetEasePwd);
                } else if (code == 415) {

                } else {
                    Toast.makeText(mActivity, "[登录失败]: " + code, Toast.LENGTH_SHORT).show();
                    //
                    String log = "[登录NIM]:登录失败(account=" + account + ", token= " + pwdNet + ", code=" + code + ")";
                    Log.e(TAG, log);
                    //
                    mWeakHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();//重新登录
                        }
                    }, 5000);
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginFailed();
                String log = "[登录NIM]:登录EXCETPION(account=" + account + ", token= " + pwdNet + ", e=" + exception.getMessage() + ")";
                Log.e(TAG, log);
            }
        });
    }

    private void onLoginFailed() {

    }

    private void saveLoginInfo(final String account, final String token) {
        AuthPreferences.saveUserAccount(account);
        AuthPreferences.saveUserToken(token);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */
    private InputConfig inputConfig = new InputConfig(false, false, false);

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        return MD5.getStringMD5(password);
    }

}
