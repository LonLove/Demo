package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description: 授课老师
 * author: GaoJie
 * created at: 2018/6/29 17:36
*/
public class Teacher implements Parcelable{
    /**
     * teacherPhone : 15288441870
     * laoShiId : e39c7ae181d4453daa5d2062cd3ffccc
     * avatar : http://7xjew0.com2.z0.glb.qiniucdn.com/6214c7e41efa402b9d0642bdec616925.jpg
     * teacherId : 8a9892835d5595ba015d5990ff7d0268
     * teacherName : 立苑
     * teachingYear : 1
     */

    private String teacherPhone;
    private String laoShiId;
    private String avatar;
    private String teacherId;
    private String teacherName;
    private int teachingYear;

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getLaoShiId() {
        return laoShiId;
    }

    public void setLaoShiId(String laoShiId) {
        this.laoShiId = laoShiId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeachingYear() {
        return teachingYear;
    }

    public void setTeachingYear(int teachingYear) {
        this.teachingYear = teachingYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teacherPhone);
        dest.writeString(this.laoShiId);
        dest.writeString(this.avatar);
        dest.writeString(this.teacherId);
        dest.writeString(this.teacherName);
        dest.writeInt(this.teachingYear);
    }

    public Teacher() {
    }

    protected Teacher(Parcel in) {
        this.teacherPhone = in.readString();
        this.laoShiId = in.readString();
        this.avatar = in.readString();
        this.teacherId = in.readString();
        this.teacherName = in.readString();
        this.teachingYear = in.readInt();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}