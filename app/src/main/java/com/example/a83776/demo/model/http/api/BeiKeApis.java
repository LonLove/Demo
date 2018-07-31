package com.example.a83776.demo.model.http.api;

import com.example.a83776.demo.model.bean.LiveRoomInfo;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/30 9:20
 */
public interface BeiKeApis {
    String HOST = ApiConfig.BASE_BEIKE;

    /**
     * 获取直播房间信息(该接口废弃了)
     *
     * @return
     */
    @FormUrlEncoded
    @POST("huoQuZhiBoFangJianXinXi")
    Flowable<LiveRoomInfo> huoQuZhiBoFangJianXinXi(@Field("jiaZhangRuankoId") String jiaZhangRuankoId,
                                                   @Field("erpDaKeBiaoKeCiUuid") String erpDaKeBiaoKeCiUuid,
                                                   @Field("laoShiRuankoId") String laoShiRuankoId,
                                                   @Field("yiXiangYongHu") Integer yiXiangYongHu);
    /**
     * 获取直播房间信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("huoQuZhiBoFangJian")
    Flowable<LiveRoomInfo> huoQuZhiBoFangJian(@Field("erpDaKeBiaoKeCiUuid") String erpDaKeBiaoKeCiUuid,
                                        @Field("laoShiRuankoId") String laoShiRuankoId);
}
