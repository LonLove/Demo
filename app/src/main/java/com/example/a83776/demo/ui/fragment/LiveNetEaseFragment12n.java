package com.example.a83776.demo.ui.fragment;

import android.os.Bundle;

import com.example.a83776.demo.R;
import com.example.a83776.demo.base.BaseNetEaseFragment;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

import java.util.Map;

/**
 * description: 网易直播端
 * author: GaoJie
 * created at: 2018/7/6 15:30
 */
public class LiveNetEaseFragment12n extends BaseNetEaseFragment {
    public static LiveNetEaseFragment12n newInstance(Bundle args) {
        LiveNetEaseFragment12n fragment12n = new LiveNetEaseFragment12n();
        fragment12n.setArguments(args);
        return fragment12n;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragmet_net12;
    }

    @Override
    protected void initEventAndData() {
        onPermissionNoGranted_();
    }

    @Override
    protected void onPermissionNoGranted_() {
        register();
//        login();
    }


    @Override
    protected void onOtherExitChatRoom() {

    }

    @Override
    protected void doCloseInteraction() {

    }

    @Override
    protected void onConnected() {

    }

    @Override
    protected void onDisconnected() {

    }

    @Override
    protected void onChannelName(int num) {

    }

    @Override
    public void onTakeSnapshotResult(String s, boolean b, String s1) {

    }

    @Override
    public void onAVRecordingCompletion(String s, String s1) {

    }

    @Override
    public void onAudioRecordingCompletion(String s) {

    }

    @Override
    public void onLowStorageSpaceWarning(long l) {

    }

    @Override
    public void onAudioMixingEvent(int i) {

    }

    @Override
    public void onJoinedChannel(int i, String s, String s1, int i1) {

    }

    @Override
    public void onUserJoined(String s) {

    }

    @Override
    public void onUserLeave(String s, int i) {

    }

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onProtocolIncompatible(int i) {

    }

    @Override
    public void onDisconnectServer(int i) {

    }

    @Override
    public void onNetworkQuality(String s, int i, AVChatNetworkStats avChatNetworkStats) {

    }

    @Override
    public void onCallEstablished() {

    }

    @Override
    public void onDeviceEvent(int i, String s) {

    }

    @Override
    public void onConnectionTypeChanged(int i) {

    }

    @Override
    public void onFirstVideoFrameAvailable(String s) {

    }

    @Override
    public void onFirstVideoFrameRendered(String s) {

    }

    @Override
    public void onVideoFrameResolutionChanged(String s, int i, int i1, int i2) {

    }

    @Override
    public void onVideoFpsReported(String s, int i) {

    }

    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame avChatVideoFrame, boolean b) {
        return false;
    }

    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame avChatAudioFrame) {
        return false;
    }

    @Override
    public void onAudioDeviceChanged(int i) {

    }

    @Override
    public void onReportSpeaker(Map<String, Integer> map, int i) {

    }

    @Override
    public void onSessionStats(AVChatSessionStats avChatSessionStats) {

    }

    @Override
    public void onLiveEvent(int i) {

    }
}
