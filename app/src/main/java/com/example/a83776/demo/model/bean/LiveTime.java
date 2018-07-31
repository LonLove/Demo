package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/29 17:37
*/

public class LiveTime implements Serializable, Parcelable {
    /**
     * classScheduleId : 8a9892835f2d5461015f567cde566c0e
     * endTimeStr : 2017-10-27 10:42:00
     * startTimeStr : 2017-10-27 10:22:00
     * startTime : 10月27日
     * curriculumDate : 2017-10-26
     * endTime : 10月27日
     * time : 10:22
     * classTime : 2
     * zhiBoZhuangTai : -1
     */

    private String classScheduleId;
    private String endTimeStr;
    private String startTimeStr;
    private String startTime;
    private String curriculumDate;
    private String endTime;
    private String time;
    private int classTime;
    private int zhiBoZhuangTai;

    public String getClassScheduleId() {
        return classScheduleId;
    }

    public void setClassScheduleId(String classScheduleId) {
        this.classScheduleId = classScheduleId;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCurriculumDate() {
        return curriculumDate;
    }

    public void setCurriculumDate(String curriculumDate) {
        this.curriculumDate = curriculumDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClassTime() {
        return classTime;
    }

    public void setClassTime(int classTime) {
        this.classTime = classTime;
    }

    public int getZhiBoZhuangTai() {
        return zhiBoZhuangTai;
    }

    public void setZhiBoZhuangTai(int zhiBoZhuangTai) {
        this.zhiBoZhuangTai = zhiBoZhuangTai;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classScheduleId);
        dest.writeString(this.endTimeStr);
        dest.writeString(this.startTimeStr);
        dest.writeString(this.startTime);
        dest.writeString(this.curriculumDate);
        dest.writeString(this.endTime);
        dest.writeString(this.time);
        dest.writeInt(this.classTime);
        dest.writeInt(this.zhiBoZhuangTai);
    }

    public LiveTime() {
    }

    protected LiveTime(Parcel in) {
        this.classScheduleId = in.readString();
        this.endTimeStr = in.readString();
        this.startTimeStr = in.readString();
        this.startTime = in.readString();
        this.curriculumDate = in.readString();
        this.endTime = in.readString();
        this.time = in.readString();
        this.classTime = in.readInt();
        this.zhiBoZhuangTai = in.readInt();
    }

    public static final Creator<LiveTime> CREATOR = new Creator<LiveTime>() {
        @Override
        public LiveTime createFromParcel(Parcel source) {
            return new LiveTime(source);
        }

        @Override
        public LiveTime[] newArray(int size) {
            return new LiveTime[size];
        }
    };
}
