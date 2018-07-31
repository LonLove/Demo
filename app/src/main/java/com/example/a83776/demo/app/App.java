package com.example.a83776.demo.app;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import com.example.a83776.demo.R;
import com.example.a83776.demo.di.component.AppComponent;
import com.example.a83776.demo.di.component.DaggerAppComponent;
import com.example.a83776.demo.di.module.AppModule;
import com.example.a83776.demo.di.module.HttpModule;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.Prefs.PreferencesHelper;
import com.example.a83776.netease.DemoCache;
import com.example.a83776.netease.base.util.ScreenUtil;
import com.example.a83776.netease.base.util.log.LogUtil;
import com.example.a83776.netease.base.util.sys.SystemUtil;
import com.example.a83776.netease.im.activity.WelcomeActivity;
import com.example.a83776.netease.im.config.AuthPreferences;
import com.example.a83776.netease.im.config.UserPreferences;
import com.example.a83776.netease.im.util.storage.StorageType;
import com.example.a83776.netease.im.util.storage.StorageUtil;
import com.example.a83776.netease.inject.FlavorDependent;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/29 17:34
 */

public class App extends Application {
    public static App instance;
    public static AppComponent sAppComponent;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //网易直播初始化
        DemoCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), getOptions());
//        // crash handler
//        AppCrashHandler.getInstance(this);
        if (inMainProcess()) {
            // 注册自定义消息附件解析器
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(FlavorDependent.getInstance().getMsgAttachmentParser());
            // init tools
            StorageUtil.init(this, null);
            ScreenUtil.init(this);
//            DemoCache.initImageLoaderKit();
            // init log
            initLog();
            FlavorDependent.getInstance().onApplicationCreate();
        }
    }
    private LoginInfo getLoginInfo() {
        String account = AuthPreferences.getUserAccount();
        String token = AuthPreferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }
        // 点击通知需要跳转到的界面
        config.notificationEntrance = WelcomeActivity.class;
        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim/";
        options.sdkStorageRootPath = sdkPath;
        Log.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo nim sdk log path=" + sdkPath);

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = (int) (0.5 * ScreenUtil.screenWidth);

        // 用户信息提供者
        options.userInfoProvider = null;

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = null;

        return options;
    }

    private boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private void initLog() {
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);
        LogUtil.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo log path=" + path);
    }
    public static AppComponent getAppComponent() {
        if (sAppComponent == null) {
            sAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return sAppComponent;
    }

    @VisibleForTesting
    public void setComponent(AppComponent component) {
        this.sAppComponent = component;
    }

    public static DataManager getDataManager() {
        return sAppComponent.getDataManager();
    }
    public static PreferencesHelper getSp() {
        return sAppComponent.preferencesHelper();
    }
}
