package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * description: 直播课（直播大厅）
 * author: GaoJie
 * created at: 2018/6/29 17:37
*/
public class Live implements Serializable, Parcelable {

    /**
     * erpClassId : 66e25c5b008f4e728a1772d4a2264d09
     * gradeText : 初一,初二,初三
     * attentionType : 1
     * subject : 生物
     * liveType : 0
     * className : 太阳花特
     * discountRecord : 100
     * duration : 10
     * classId : 8a9892835f2d5461015f567c848c6c0a
     * times : 0
     * videoUrl : null
     * coursePrice : 30
     * marketCourseId : 8a9892835dbc56d1015def98dd0a55df
     * seasonTicketPrice : 120
     * courseId : 8a9892835dbc56d1015def950dee55b2
     * marketCourseName : 课程名称变动
     * timeList : [{"classScheduleId":"8a9892835f2d5461015f567cde566c0e","endTimeStr":"2017-10-27 10:42:00","startTimeStr":"2017-10-27 10:22:00","startTime":"10月27日","curriculumDate":"2017-10-26","endTime":"10月27日","time":"10:22","classTime":2,"zhiBoZhuangTai":-1},{"classScheduleId":"8a9892835f2d5461015f567cdefc6c15","endTimeStr":"2017-10-28 10:42:00","startTimeStr":"2017-10-28 10:22:00","startTime":"10月28日","curriculumDate":"2017-10-26","endTime":"10月28日","time":"10:22","classTime":2,"zhiBoZhuangTai":-1}]
     * auditionClassTitle : null
     * introduce : 大范甘迪好地方好的好的发挥得很有
     * pictureDetails : Fp7qBwQQxBFgQZNLJLYqPpLKdRUx
     * nianJi : 初二
     * subText : 生物,语文
     * picture : http://7xjew0.com2.z0.glb.qiniucdn.com/Fs_mnQjfKk_Wx8ezS-oyfbLHdON3
     * baseBuyerNum : 10000014
     * sectionText : 初中
     * courseDuration : 10
     * attentionId : ff8080815f5cbed3015f5cc3cb190033
     * buyType : 1
     * recordMultiTickets : 120
     * auditionClassVideo : null
     */
    private int classScheduleType;//1说明还有未结束的课次  0表示都结束了
    private String erpClassId;
    private String gradeText;
    private int attentionType;
    private String subject;
    private int liveType;
    private String className;
    private double discountRecord;
    private int duration;
    private String classId;
    private int times;
    private String videoUrl;
    private String coursePrice;
    private String marketCourseId;
    private double seasonTicketPrice;
    private String courseId;
    private String marketCourseName;
    private String auditionClassTitle;
    private String introduce;
    private String pictureDetails;
    private String nianJi;
    private String subText;
    private String picture;
    private int baseBuyerNum;
    private String sectionText;
    private String courseDuration;
    private String attentionId;
    private int buyType;
    private double recordMultiTickets;
    private String auditionClassVideo;
    private List<LiveTime> timeList;
    private String tempEndTimeStr;

    public int getClassScheduleType() {
        return classScheduleType;
    }

    public void setClassScheduleType(int classScheduleType) {
        this.classScheduleType = classScheduleType;
    }

    public String getTempEndTimeStr() {
        return tempEndTimeStr;
    }

    public void setTempEndTimeStr(String tempEndTimeStr) {
        this.tempEndTimeStr = tempEndTimeStr;
    }

    public String getErpClassId() {
        return erpClassId;
    }

    public void setErpClassId(String erpClassId) {
        this.erpClassId = erpClassId;
    }

    public String getGradeText() {
        return gradeText;
    }

    public void setGradeText(String gradeText) {
        this.gradeText = gradeText;
    }

    public int getAttentionType() {
        return attentionType;
    }

    public void setAttentionType(int attentionType) {
        this.attentionType = attentionType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLiveType() {
        return liveType;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getDiscountRecord() {
        return discountRecord;
    }

    public void setDiscountRecord(int discountRecord) {
        this.discountRecord = discountRecord;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getMarketCourseId() {
        return marketCourseId;
    }

    public void setMarketCourseId(String marketCourseId) {
        this.marketCourseId = marketCourseId;
    }

    public double getSeasonTicketPrice() {
        return seasonTicketPrice;
    }

    public void setSeasonTicketPrice(int seasonTicketPrice) {
        this.seasonTicketPrice = seasonTicketPrice;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(String pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public String getNianJi() {
        return nianJi;
    }

    public void setNianJi(String nianJi) {
        this.nianJi = nianJi;
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

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(String attentionId) {
        this.attentionId = attentionId;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public double getRecordMultiTickets() {
        return recordMultiTickets;
    }

    public void setRecordMultiTickets(int recordMultiTickets) {
        this.recordMultiTickets = recordMultiTickets;
    }

    public String getAuditionClassVideo() {
        return auditionClassVideo;
    }

    public void setAuditionClassVideo(String auditionClassVideo) {
        this.auditionClassVideo = auditionClassVideo;
    }

    public List<LiveTime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<LiveTime> timeList) {
        this.timeList = timeList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.erpClassId);
        dest.writeString(this.gradeText);
        dest.writeInt(this.attentionType);
        dest.writeString(this.subject);
        dest.writeInt(this.liveType);
        dest.writeString(this.className);
        dest.writeDouble(this.discountRecord);
        dest.writeInt(this.duration);
        dest.writeString(this.classId);
        dest.writeInt(this.times);
        dest.writeString(this.videoUrl);
        dest.writeString(this.coursePrice);
        dest.writeString(this.marketCourseId);
        dest.writeDouble(this.seasonTicketPrice);
        dest.writeString(this.courseId);
        dest.writeString(this.marketCourseName);
        dest.writeString(this.auditionClassTitle);
        dest.writeString(this.introduce);
        dest.writeString(this.pictureDetails);
        dest.writeString(this.nianJi);
        dest.writeString(this.subText);
        dest.writeString(this.picture);
        dest.writeInt(this.baseBuyerNum);
        dest.writeString(this.sectionText);
        dest.writeString(this.courseDuration);
        dest.writeString(this.attentionId);
        dest.writeInt(this.buyType);
        dest.writeDouble(this.recordMultiTickets);
        dest.writeString(this.auditionClassVideo);
        dest.writeTypedList(this.timeList);
    }

    public Live() {
    }

    protected Live(Parcel in) {
        this.erpClassId = in.readString();
        this.gradeText = in.readString();
        this.attentionType = in.readInt();
        this.subject = in.readString();
        this.liveType = in.readInt();
        this.className = in.readString();
        this.discountRecord = in.readDouble();
        this.duration = in.readInt();
        this.classId = in.readString();
        this.times = in.readInt();
        this.videoUrl = in.readString();
        this.coursePrice = in.readString();
        this.marketCourseId = in.readString();
        this.seasonTicketPrice = in.readDouble();
        this.courseId = in.readString();
        this.marketCourseName = in.readString();
        this.auditionClassTitle = in.readString();
        this.introduce = in.readString();
        this.pictureDetails = in.readString();
        this.nianJi = in.readString();
        this.subText = in.readString();
        this.picture = in.readString();
        this.baseBuyerNum = in.readInt();
        this.sectionText = in.readString();
        this.courseDuration = in.readString();
        this.attentionId = in.readString();
        this.buyType = in.readInt();
        this.recordMultiTickets = in.readDouble();
        this.auditionClassVideo = in.readString();
        this.timeList = in.createTypedArrayList(LiveTime.CREATOR);
    }

    public static final Creator<Live> CREATOR = new Creator<Live>() {
        @Override
        public Live createFromParcel(Parcel source) {
            return new Live(source);
        }

        @Override
        public Live[] newArray(int size) {
            return new Live[size];
        }
    };
}
