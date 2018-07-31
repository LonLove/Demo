package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/29 17:36
*/

public class Recording  implements Serializable ,Parcelable{
    //         "fileName": "100050970_20170912022622_405.mp4",
    //                 "luBoUrl":"http://218.17.158.37:18888/201709/a1e93c48b768405d884488591c649f8a_022349100050970_20170912022622_405.mp4        /",
    //                 "zhiBoPingTai": 1,//直播平台  0网易 1声网
    //                 "luBoFengMianUrl": "http://218.17.158.37:8880/marketGateway/imgs/luBoVideoFengMianIcon.png"
    public String fileName;
    public String luBoUrl;
    public String zhiBoPingTai;
    public String luBoFengMianUrl;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLuBoUrl() {
        return luBoUrl;
    }

    public void setLuBoUrl(String luBoUrl) {
        this.luBoUrl = luBoUrl;
    }

    public String getZhiBoPingTai() {
        return zhiBoPingTai;
    }

    public void setZhiBoPingTai(String zhiBoPingTai) {
        this.zhiBoPingTai = zhiBoPingTai;
    }

    public String getLuBoFengMianUrl() {
        return luBoFengMianUrl;
    }

    public void setLuBoFengMianUrl(String luBoFengMianUrl) {
        this.luBoFengMianUrl = luBoFengMianUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
        dest.writeString(this.luBoUrl);
        dest.writeString(this.zhiBoPingTai);
        dest.writeString(this.luBoFengMianUrl);
    }

    public Recording() {
    }

    protected Recording(Parcel in) {
        this.fileName = in.readString();
        this.luBoUrl = in.readString();
        this.zhiBoPingTai = in.readString();
        this.luBoFengMianUrl = in.readString();
    }

    public static final Creator<Recording> CREATOR = new Creator<Recording>() {
        @Override
        public Recording createFromParcel(Parcel source) {
            return new Recording(source);
        }

        @Override
        public Recording[] newArray(int size) {
            return new Recording[size];
        }
    };
}
