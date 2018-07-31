package com.example.a83776.demo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
 
import java.io.Serializable;

/**
 * Created by admin on 2017/12/1.
 */

public class JiaZhangKeBiaoListBean implements Parcelable, Serializable {
    String erpDaKeBiaoKeCiUuid;
    String banJiMingCheng;
    String keChengMingCheng;
    String laoShiXingMing;
    int laoShiRuanKoId;
    int isHaveQuanXian;
    float keChengShouJia;

    public String getErpDaKeBiaoKeCiUuid() {
        return erpDaKeBiaoKeCiUuid;
    }

    public void setErpDaKeBiaoKeCiUuid(String erpDaKeBiaoKeCiUuid) {
        this.erpDaKeBiaoKeCiUuid = erpDaKeBiaoKeCiUuid;
    }

    public String getBanJiMingCheng() {
        return banJiMingCheng;
    }

    public void setBanJiMingCheng(String banJiMingCheng) {
        this.banJiMingCheng = banJiMingCheng;
    }

    public String getKeChengMingCheng() {
        return keChengMingCheng;
    }

    public void setKeChengMingCheng(String keChengMingCheng) {
        this.keChengMingCheng = keChengMingCheng;
    }

    public String getLaoShiXingMing() {
        return laoShiXingMing;
    }

    public void setLaoShiXingMing(String laoShiXingMing) {
        this.laoShiXingMing = laoShiXingMing;
    }

    public int getLaoShiRuanKoId() {
        return laoShiRuanKoId;
    }

    public void setLaoShiRuanKoId(int laoShiRuanKoId) {
        this.laoShiRuanKoId = laoShiRuanKoId;
    }

    public int getIsHaveQuanXian() {
        return isHaveQuanXian;
    }

    public void setIsHaveQuanXian(int isHaveQuanXian) {
        this.isHaveQuanXian = isHaveQuanXian;
    }

    public float getKeChengShouJia() {
        return keChengShouJia;
    }

    public void setKeChengShouJia(float keChengShouJia) {
        this.keChengShouJia = keChengShouJia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.erpDaKeBiaoKeCiUuid);
        dest.writeString(this.banJiMingCheng);
        dest.writeString(this.keChengMingCheng);
        dest.writeString(this.laoShiXingMing);
        dest.writeInt(this.laoShiRuanKoId);
        dest.writeInt(this.isHaveQuanXian);
        dest.writeFloat(this.keChengShouJia);
    }

    public JiaZhangKeBiaoListBean() {
    }

    protected JiaZhangKeBiaoListBean(Parcel in) {
        this.erpDaKeBiaoKeCiUuid = in.readString();
        this.banJiMingCheng = in.readString();
        this.keChengMingCheng = in.readString();
        this.laoShiXingMing = in.readString();
        this.laoShiRuanKoId = in.readInt();
        this.isHaveQuanXian = in.readInt();
        this.keChengShouJia = in.readFloat();
    }

    public static final Creator<JiaZhangKeBiaoListBean> CREATOR = new Creator<JiaZhangKeBiaoListBean>() {
        @Override
        public JiaZhangKeBiaoListBean createFromParcel(Parcel source) {
            return new JiaZhangKeBiaoListBean(source);
        }

        @Override
        public JiaZhangKeBiaoListBean[] newArray(int size) {
            return new JiaZhangKeBiaoListBean[size];
        }
    };
}
