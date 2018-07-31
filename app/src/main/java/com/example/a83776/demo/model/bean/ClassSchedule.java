package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * description: 课程列表
 * author: GaoJie
 * created at: 2018/6/29 17:37
*/
public class ClassSchedule implements Serializable, Parcelable {
    /**
     * gradeText : 初一,初二,初三
     * endTimeStrs : 10月27日 10:42
     * monthDay : 10月27日
     * description : 第1节
     * startTimeStr : 2017-10-27 10:22:00
     * className : 太阳花特
     * cDescription : null
     * discountRecord : 100
     * duration : 10
     * erpDaKeBiaoKeCiUuid : 55ed7be540f44f12a92eecb748da446e
     * startTimeStrs : 10月27日 10:22
     * organizationId : 8a9892835d5595ba015d598322c10203
     * classId : 8a9892835f2d5461015f567c848c6c0a
     * classScheduleId : 8a9892835f2d5461015f567cde566c0e
     * teacherRuankoUserId : 10026251
     * videoUrl : null
     * datOfWeek : 周五
     * marketCourseId : 8a9892835dbc56d1015def98dd0a55df
     * startTime : 10:22
     * courseId : 8a9892835dbc56d1015def950dee55b2
     * unitPrice : 30
     * marketCourseName : 课程名称变动
     * auditionClassTitle : null
     * address : 广东深圳
     * ticketPrice : 60
     * organizationName : 立苑花园辅导中心
     * teacherName : 立苑
     * introduce : 大范甘迪好地方好的好的发挥得很有
     * pictureDetails : http://7xjew0.com2.z0.glb.qiniucdn.com/Fp7qBwQQxBFgQZNLJLYqPpLKdRUx
     * subText : 生物,语文
     * picture : http://7xjew0.com2.z0.glb.qiniucdn.com/Fs_mnQjfKk_Wx8ezS-oyfbLHdON3
     * courseHour : 2
     * baseBuyerNum : 10000015
     * sectionText : 初中
     * courseName : 课程名称变动
     * teacherRuanKuUserName : otuoavwruu
     * recordingList : []
     * videoRecord : null
     * endTimeStr : 2017-10-27 10:42:00
     * viewType : 0
     * buyType : 2
     * recordMultiTickets : 120
     * auditionClassVideo : null
     * endTime : 10:42
     * zhiBoZhuangTai : -1
     */

    private String gradeText;
    private String endTimeStrs;
    private String monthDay;
    private String description;
    private String startTimeStr;
    private String className;
    private String cDescription;
    private int discountRecord;
    private int duration;
    private String erpDaKeBiaoKeCiUuid;
    private String startTimeStrs;
    private String organizationId;
    private String classId;
    private String classScheduleId;
    private int teacherRuankoUserId;
    private String videoUrl;
    private String datOfWeek;
    private String marketCourseId;
    private String startTime;
    private String courseId;
    private int unitPrice;
    private String marketCourseName;
    private String auditionClassTitle;
    private String address;
    private int ticketPrice;
    private String organizationName;
    private String teacherName;
    private String introduce;
    private String pictureDetails;
    private String subText;
    private String picture;
    private int courseHour;
    private int baseBuyerNum;
    private String sectionText;
    private String courseName;
    private String teacherRuanKuUserName;
    private String videoRecord;
    private String endTimeStr;
    private int viewType;
    private int buyType;
    private int recordMultiTickets;
    private String auditionClassVideo;
    private String endTime;
    private int zhiBoZhuangTai;
    private List<Recording> recordingList;
    private int isFalseLive; //是否是假直播 1是假直播

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

    public String getEndTimeStrs() {
        return endTimeStrs;
    }

    public void setEndTimeStrs(String endTimeStrs) {
        this.endTimeStrs = endTimeStrs;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCDescription() {
        return cDescription;
    }

    public void setCDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public int getDiscountRecord() {
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

    public String getErpDaKeBiaoKeCiUuid() {
        return erpDaKeBiaoKeCiUuid;
    }

    public void setErpDaKeBiaoKeCiUuid(String erpDaKeBiaoKeCiUuid) {
        this.erpDaKeBiaoKeCiUuid = erpDaKeBiaoKeCiUuid;
    }

    public String getStartTimeStrs() {
        return startTimeStrs;
    }

    public void setStartTimeStrs(String startTimeStrs) {
        this.startTimeStrs = startTimeStrs;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassScheduleId() {
        return classScheduleId;
    }

    public void setClassScheduleId(String classScheduleId) {
        this.classScheduleId = classScheduleId;
    }

    public int getTeacherRuankoUserId() {
        return teacherRuankoUserId;
    }

    public void setTeacherRuankoUserId(int teacherRuankoUserId) {
        this.teacherRuankoUserId = teacherRuankoUserId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDatOfWeek() {
        return datOfWeek;
    }

    public void setDatOfWeek(String datOfWeek) {
        this.datOfWeek = datOfWeek;
    }

    public String getMarketCourseId() {
        return marketCourseId;
    }

    public void setMarketCourseId(String marketCourseId) {
        this.marketCourseId = marketCourseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public int getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(int courseHour) {
        this.courseHour = courseHour;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherRuanKuUserName() {
        return teacherRuanKuUserName;
    }

    public void setTeacherRuanKuUserName(String teacherRuanKuUserName) {
        this.teacherRuanKuUserName = teacherRuanKuUserName;
    }

    public String getVideoRecord() {
        return videoRecord;
    }

    public void setVideoRecord(String videoRecord) {
        this.videoRecord = videoRecord;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
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

    public int getRecordMultiTickets() {
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getZhiBoZhuangTai() {
        return zhiBoZhuangTai;
    }

    public void setZhiBoZhuangTai(int zhiBoZhuangTai) {
        this.zhiBoZhuangTai = zhiBoZhuangTai;
    }

    public List<Recording> getRecordingList() {
        return recordingList;
    }

    public void setRecordingList(List<Recording> recordingList) {
        this.recordingList = recordingList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gradeText);
        dest.writeString(this.endTimeStrs);
        dest.writeString(this.monthDay);
        dest.writeString(this.description);
        dest.writeString(this.startTimeStr);
        dest.writeString(this.className);
        dest.writeString(this.cDescription);
        dest.writeInt(this.discountRecord);
        dest.writeInt(this.duration);
        dest.writeString(this.erpDaKeBiaoKeCiUuid);
        dest.writeString(this.startTimeStrs);
        dest.writeString(this.organizationId);
        dest.writeString(this.classId);
        dest.writeString(this.classScheduleId);
        dest.writeInt(this.teacherRuankoUserId);
        dest.writeString(this.videoUrl);
        dest.writeString(this.datOfWeek);
        dest.writeString(this.marketCourseId);
        dest.writeString(this.startTime);
        dest.writeString(this.courseId);
        dest.writeInt(this.unitPrice);
        dest.writeString(this.marketCourseName);
        dest.writeString(this.auditionClassTitle);
        dest.writeString(this.address);
        dest.writeInt(this.ticketPrice);
        dest.writeString(this.organizationName);
        dest.writeString(this.teacherName);
        dest.writeString(this.introduce);
        dest.writeString(this.pictureDetails);
        dest.writeString(this.subText);
        dest.writeString(this.picture);
        dest.writeInt(this.courseHour);
        dest.writeInt(this.baseBuyerNum);
        dest.writeString(this.sectionText);
        dest.writeString(this.courseName);
        dest.writeString(this.teacherRuanKuUserName);
        dest.writeString(this.videoRecord);
        dest.writeString(this.endTimeStr);
        dest.writeInt(this.viewType);
        dest.writeInt(this.buyType);
        dest.writeInt(this.recordMultiTickets);
        dest.writeString(this.auditionClassVideo);
        dest.writeString(this.endTime);
        dest.writeInt(this.zhiBoZhuangTai);
        dest.writeTypedList(this.recordingList);
    }

    public ClassSchedule() {
    }

    protected ClassSchedule(Parcel in) {
        this.gradeText = in.readString();
        this.endTimeStrs = in.readString();
        this.monthDay = in.readString();
        this.description = in.readString();
        this.startTimeStr = in.readString();
        this.className = in.readString();
        this.cDescription = in.readString();
        this.discountRecord = in.readInt();
        this.duration = in.readInt();
        this.erpDaKeBiaoKeCiUuid = in.readString();
        this.startTimeStrs = in.readString();
        this.organizationId = in.readString();
        this.classId = in.readString();
        this.classScheduleId = in.readString();
        this.teacherRuankoUserId = in.readInt();
        this.videoUrl = in.readString();
        this.datOfWeek = in.readString();
        this.marketCourseId = in.readString();
        this.startTime = in.readString();
        this.courseId = in.readString();
        this.unitPrice = in.readInt();
        this.marketCourseName = in.readString();
        this.auditionClassTitle = in.readString();
        this.address = in.readString();
        this.ticketPrice = in.readInt();
        this.organizationName = in.readString();
        this.teacherName = in.readString();
        this.introduce = in.readString();
        this.pictureDetails = in.readString();
        this.subText = in.readString();
        this.picture = in.readString();
        this.courseHour = in.readInt();
        this.baseBuyerNum = in.readInt();
        this.sectionText = in.readString();
        this.courseName = in.readString();
        this.teacherRuanKuUserName = in.readString();
        this.videoRecord = in.readString();
        this.endTimeStr = in.readString();
        this.viewType = in.readInt();
        this.buyType = in.readInt();
        this.recordMultiTickets = in.readInt();
        this.auditionClassVideo = in.readString();
        this.endTime = in.readString();
        this.zhiBoZhuangTai = in.readInt();
        this.recordingList = in.createTypedArrayList(Recording.CREATOR);
    }

    public static final Creator<ClassSchedule> CREATOR = new Creator<ClassSchedule>() {
        @Override
        public ClassSchedule createFromParcel(Parcel source) {
            return new ClassSchedule(source);
        }

        @Override
        public ClassSchedule[] newArray(int size) {
            return new ClassSchedule[size];
        }
    };
}