package com.example.a83776.demo.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.example.a83776.demo.base.SimpleActivity;
import com.example.a83776.demo.util.HomeKeyWatcher;

/**
 * description: 在此Activity中，如果视频正在播放或者缓冲，按下home键，暂停视频播放，回到此Activity后继续播放视频，
 * 如果离开此Activity(跳转到其他Activity或按下Back键)，则释放视频播放器
 * author: GaoJie
 * created at: 2018/6/29 16:39
 */
public abstract class CompatHomeKeyActivity extends SimpleActivity {

    private HomeKeyWatcher mHomeKeyWatcher;
    private boolean pressHome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mHomeKeyWatcher = new HomeKeyWatcher(this);
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressHome = true;
            }
        });
        pressHome = false;
        if (mHomeKeyWatcher != null) {
            mHomeKeyWatcher.startWatch();
        }
    }

    @Override
    protected void onStop() {
        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
        /*if (pressedHome) {
            NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
        } else {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }*/
        super.onStop();
        if (mHomeKeyWatcher != null) {
            mHomeKeyWatcher.stopWatch();
        }
    }

    @Override
    protected void onRestart() {
        if (mHomeKeyWatcher != null) {
            mHomeKeyWatcher.startWatch();
        }
        pressHome = false;
        super.onRestart();
        //NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
    }

    public boolean isPressedHome() {
        return pressHome;
    }
  /*  @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }*/


}
