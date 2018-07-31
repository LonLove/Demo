package com.example.a83776.demo.model;

import com.example.a83776.demo.model.bean.Book;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.model.http.HttpHelper;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 15:20
 */
public class DataManager implements HttpHelper {
    HttpHelper mHttpHelper;

    /**
     * 构造方法创建对象 see {@link com.example.a83776.demo.di.module.AppModule#providerDataManager(HttpHelper)}
     *
     * @param httpHelper
     */
    public DataManager(HttpHelper httpHelper) {
        this.mHttpHelper = httpHelper;
    }

    @Override
    public Flowable<Book> getSearchBook(String name, String tag, int start, int count) {
        return mHttpHelper.getSearchBook(name, tag, start, count);
    }

    @Override
    public Flowable<WeiDianHttpResponse<List<Live>>> liveCourse(int type, int pageNo, int pageSize, String from, String channel, Integer studySection) {
        return mHttpHelper.liveCourse(type, pageNo, pageSize, from, channel, studySection);
    }

    @Override
    public Flowable<WeiDianHttpResponse<LiveDetail>> liveCourseDetail(String id) {
        return mHttpHelper.liveCourseDetail(id);
    }

    @Override
    public Flowable<LiveRoomInfo> huoQuZhiBoFangJianXinXi(String jiaZhangRuankoId, String erpDaKeBiaoKeCiUuid, String laoShiRuankoId) {
        return mHttpHelper.huoQuZhiBoFangJianXinXi(jiaZhangRuankoId, erpDaKeBiaoKeCiUuid, laoShiRuankoId);
    }

    @Override
    public Flowable<LiveRoomInfo> huoQuZhiBoFangJian(String erpDaKeBiaoKeCiUuid, String laoShiRuankoId) {
        return mHttpHelper.huoQuZhiBoFangJian(erpDaKeBiaoKeCiUuid, laoShiRuankoId);
    }
}
