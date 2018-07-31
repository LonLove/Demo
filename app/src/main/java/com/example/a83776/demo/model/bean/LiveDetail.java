package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
/**
 * description: 直播课详情
 * author: GaoJie
 * created at: 2018/6/29 17:35
*/

public class LiveDetail  implements Parcelable {

    private String gradeText;
    private int isHaveLive;//没有正在直播时为-1，有直播时为课次下标
    private String rtmpPullUrl;
    private String description;
    private float moneyCount;
    private String className;
    private int discountRecord;
    private String erpDaKeBiaoKeCiUuid;
    private String videoUrl;
    private int seasonTicketPrice;
    private String marketCourseName;
    private String auditionClassTitle;
    private String hlsPullUrl;
    private String address;
    private String organizationName;
    private String pictureDetails;
    private String subText;
    private String picture;
    private int baseBuyerNum;
    private String sectionText;
    private int viewType;
    private int buyType;
    private String auditionClassVideo;
    private int recordMultiTickets;
    private int classScheduleCount;
    private List<ClassSchedule> classScheduleList;
    private List<Teacher> teacherList;
    private String videoRecord;
    private int isFalseLive;//是否是假直播 1是假直播

    public String getVideoRecord() {
        return videoRecord;
    }

    public void setVideoRecord(String videoRecord) {
        this.videoRecord = videoRecord;
    }

    public int getIsFalseLive() {
        return isFalseLive;
    }

    public void setIsFalseLive(int isFalseLive) {
        this.isFalseLive = isFalseLive;
    }

    public String getGradeText() {
        return gradeText;
    }

    public void setGradeText(String gradeText) {
        this.gradeText = gradeText;
    }

    public int getIsHaveLive() {
        return isHaveLive;
    }

    public void setIsHaveLive(int isHaveLive) {
        this.isHaveLive = isHaveLive;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(float moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDiscountRecord() {
        return discountRecord;
    }

    public void setDiscountRecord(int discountRecord) {
        this.discountRecord = discountRecord;
    }

    public String getErpDaKeBiaoKeCiUuid() {
        return erpDaKeBiaoKeCiUuid;
    }

    public void setErpDaKeBiaoKeCiUuid(String erpDaKeBiaoKeCiUuid) {
        this.erpDaKeBiaoKeCiUuid = erpDaKeBiaoKeCiUuid;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getSeasonTicketPrice() {
        return seasonTicketPrice;
    }

    public void setSeasonTicketPrice(int seasonTicketPrice) {
        this.seasonTicketPrice = seasonTicketPrice;
    }

    public String getMarketCourseName() {
        return marketCourseName;
    }

    public void setMarketCourseName(String marketCourseName) {
        this.marketCourseName = marketCourseName;
    }

    public String getAuditionClassTitle() {
        return auditionClassTitle;
    }

    public void setAuditionClassTitle(String auditionClassTitle) {
        this.auditionClassTitle = auditionClassTitle;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(String pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getBaseBuyerNum() {
        return baseBuyerNum;
    }

    public void setBaseBuyerNum(int baseBuyerNum) {
        this.baseBuyerNum = baseBuyerNum;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public String getAuditionClassVideo() {
        return auditionClassVideo;
    }

    public void setAuditionClassVideo(String auditionClassVideo) {
        this.auditionClassVideo = auditionClassVideo;
    }

    public int getRecordMultiTickets() {
        return recordMultiTickets;
    }

    public void setRecordMultiTickets(int recordMultiTickets) {
        this.recordMultiTickets = recordMultiTickets;
    }

    public int getClassScheduleCount() {
        return classScheduleCount;
    }

    public void setClassScheduleCount(int classScheduleCount) {
        this.classScheduleCount = classScheduleCount;
    }

    public List<ClassSchedule> getClassScheduleList() {
        return classScheduleList;
    }

    public void setClassScheduleList(List<ClassSchedule> classScheduleList) {
        this.classScheduleList = classScheduleList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gradeText);
        dest.writeInt(this.isHaveLive);
        dest.writeString(this.rtmpPullUrl);
        dest.writeString(this.description);
        dest.writeFloat(this.moneyCount);
        dest.writeString(this.className);
        dest.writeInt(this.discountRecord);
        dest.writeString(this.erpDaKeBiaoKeCiUuid);
        dest.writeString(this.videoUrl);
        dest.writeInt(this.seasonTicketPrice);
        dest.writeString(this.marketCourseName);
        dest.writeString(this.auditionClassTitle);
        dest.writeString(this.hlsPullUrl);
        dest.writeString(this.address);
        dest.writeString(this.organizationName);
        dest.writeString(this.pictureDetails);
        dest.writeString(this.subText);
        dest.writeString(this.picture);
        dest.writeInt(this.baseBuyerNum);
        dest.writeString(this.sectionText);
        dest.writeInt(this.viewType);
        dest.writeInt(this.buyType);
        dest.writeString(this.auditionClassVideo);
        dest.writeInt(this.recordMultiTickets);
        dest.writeInt(this.classScheduleCount);
        dest.writeTypedList(this.classScheduleList);
        dest.writeList(this.teacherList);
    }

    public LiveDetail() {
    }

    protected LiveDetail(Parcel in) {
        this.gradeText = in.readString();
        this.isHaveLive = in.readInt();
        this.rtmpPullUrl = in.readString();
        this.description = in.readString();
        this.moneyCount = in.readInt();
        this.className = in.readString();
        this.discountRecord = in.readInt();
        this.erpDaKeBiaoKeCiUuid = in.readString();
        this.videoUrl = in.readString();
        this.seasonTicketPrice = in.readInt();
        this.marketCourseName = in.readString();
        this.auditionClassTitle = in.readString();
        this.hlsPullUrl = in.readString();
        this.address = in.readString();
        this.organizationName = in.readString();
        this.pictureDetails = in.readString();
        this.subText = in.readString();
        this.picture = in.readString();
        this.baseBuyerNum = in.readInt();
        this.sectionText = in.readString();
        this.viewType = in.readInt();
        this.buyType = in.readInt();
        this.auditionClassVideo = in.readString();
        this.recordMultiTickets = in.readInt();
        this.classScheduleCount = in.readInt();
        this.classScheduleList = in.createTypedArrayList(ClassSchedule.CREATOR);
        this.teacherList = new ArrayList<Teacher>();
        in.readList(this.teacherList, Teacher.class.getClassLoader());
    }

    public static final Creator<LiveDetail> CREATOR = new Creator<LiveDetail>() {
        @Override
        public LiveDetail createFromParcel(Parcel source) {
            return new LiveDetail(source);
        }

        @Override
        public LiveDetail[] newArray(int size) {
            return new LiveDetail[size];
        }
    };
}
