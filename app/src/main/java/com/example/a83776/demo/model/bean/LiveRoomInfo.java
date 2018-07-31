package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/29 17:37
*/

public class LiveRoomInfo extends BaseResult implements Parcelable, Serializable {
    private Integer isHaveQuanXian;
    private String liveAppId;
    private String liveChannelName;
    private String liveChannelKey;
    private Integer zhiBoYongHuId;
    private Integer pinKeLeiXing;//拼课类型(0:一对一，1：小组课，2：多科强化班 3：影课  4：托管);
    private Integer laoShiRuankoId;

    private String liveClassChatGroupId;//直播房间群id


    //0：网易，1：声网
    private int zhiBoPingTai;
    /**
     * channelName : c6b27684e25b4a928fdf4e04a80fda02
     * liveAppKey : dad0ceba0d15
     * zhiBoPingTaiName : 网易
     * channelId : bab10c3fd00c4fdd9b6ab3b4c2c01ed8
     * roomId : 8933148
     */

    private String channelName;

    private String liveAppKey;
    private String zhiBoPingTaiName;
    private String channelId;
    private String roomId;
    /**
     * rtmpPullUrl : rtmp://ve9c3f823.live.126.net/live/da7fb1d3dea84f4a9b659dbad61fe05e
     * pushUrl : rtmp://pe9c3f823.live.126.net/live/da7fb1d3dea84f4a9b659dbad61fe05e?wsSecret=da219abb4275d414f65f2110b5466c71
     * zhiBoZhuangTai : 1
     */

    private String rtmpPullUrl;
    private String pushUrl;
    //1正在直播中 ,2切换平台中, 3直播结束
    private int zhiBoZhuangTai;

    public String getLiveClassChatGroupId() {
        return liveClassChatGroupId;
    }

    public void setLiveClassChatGroupId(String liveClassChatGroupId) {
        this.liveClassChatGroupId = liveClassChatGroupId;
    }

    public Integer getIsHaveQuanXian() {
        return isHaveQuanXian;
    }

    public void setIsHaveQuanXian(Integer isHaveQuanXian) {
        this.isHaveQuanXian = isHaveQuanXian;
    }

    public String getLiveAppId() {
        return liveAppId;
    }

    public void setLiveAppId(String liveAppId) {
        this.liveAppId = liveAppId;
    }

    public String getLiveChannelName() {
        return liveChannelName;
    }

    public void setLiveChannelName(String liveChannelName) {
        this.liveChannelName = liveChannelName;
    }

    public String getLiveChannelKey() {
        return liveChannelKey;
    }

    public void setLiveChannelKey(String liveChannelKey) {
        this.liveChannelKey = liveChannelKey;
    }

    public Integer getZhiBoYongHuId() {
        return zhiBoYongHuId;
    }

    public void setZhiBoYongHuId(Integer zhiBoYongHuId) {
        this.zhiBoYongHuId = zhiBoYongHuId;
    }

    public Integer getLaoShiRuankoId() {
        return laoShiRuankoId;
    }

    public void setLaoShiRuankoId(Integer laoShiRuankoId) {
        this.laoShiRuankoId = laoShiRuankoId;
    }

    public int getZhiBoPingTai() {
        return zhiBoPingTai;
    }

    public void setZhiBoPingTai(int zhiBoPingTai) {
        this.zhiBoPingTai = zhiBoPingTai;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLiveAppKey() {
        return liveAppKey;
    }

    public void setLiveAppKey(String liveAppKey) {
        this.liveAppKey = liveAppKey;
    }

    public String getZhiBoPingTaiName() {
        return zhiBoPingTaiName;
    }

    public void setZhiBoPingTaiName(String zhiBoPingTaiName) {
        this.zhiBoPingTaiName = zhiBoPingTaiName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public int getZhiBoZhuangTai() {
        return zhiBoZhuangTai;
    }

    public void setZhiBoZhuangTai(int zhiBoZhuangTai) {
        this.zhiBoZhuangTai = zhiBoZhuangTai;
    }

    public Integer getPinKeLeiXing() {
        return pinKeLeiXing;
    }

    public void setPinKeLeiXing(Integer pinKeLeiXing) {
        this.pinKeLeiXing = pinKeLeiXing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isHaveQuanXian);
        dest.writeString(this.liveAppId);
        dest.writeString(this.liveChannelName);
        dest.writeString(this.liveChannelKey);
        dest.writeValue(this.zhiBoYongHuId);
        dest.writeValue(this.pinKeLeiXing);
        dest.writeValue(this.laoShiRuankoId);
        dest.writeInt(this.zhiBoPingTai);
        dest.writeString(this.channelName);
        dest.writeString(this.liveAppKey);
        dest.writeString(this.zhiBoPingTaiName);
        dest.writeString(this.channelId);
        dest.writeString(this.roomId);
        dest.writeString(this.rtmpPullUrl);
        dest.writeString(this.pushUrl);
        dest.writeInt(this.zhiBoZhuangTai);
        dest.writeString(this.liveClassChatGroupId);
    }

    public LiveRoomInfo() {
    }

    protected LiveRoomInfo(Parcel in) {
        this.isHaveQuanXian = (Integer) in.readValue(Integer.class.getClassLoader());
        this.liveAppId = in.readString();
        this.liveChannelName = in.readString();
        this.liveChannelKey = in.readString();
        this.zhiBoYongHuId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pinKeLeiXing = (Integer) in.readValue(Integer.class.getClassLoader());
        this.laoShiRuankoId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.zhiBoPingTai = in.readInt();
        this.channelName = in.readString();
        this.liveAppKey = in.readString();
        this.zhiBoPingTaiName = in.readString();
        this.channelId = in.readString();
        this.roomId = in.readString();
        this.rtmpPullUrl = in.readString();
        this.pushUrl = in.readString();
        this.zhiBoZhuangTai = in.readInt();
        this.liveClassChatGroupId = in.readString();
    }

    public static final Creator<LiveRoomInfo> CREATOR = new Creator<LiveRoomInfo>() {
        @Override
        public LiveRoomInfo createFromParcel(Parcel source) {
            return new LiveRoomInfo(source);
        }

        @Override
        public LiveRoomInfo[] newArray(int size) {
            return new LiveRoomInfo[size];
        }
    };
}
