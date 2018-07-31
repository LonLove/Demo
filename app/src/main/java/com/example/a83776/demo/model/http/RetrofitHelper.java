package com.example.a83776.demo.model.http;

import com.example.a83776.demo.model.bean.Book;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.model.http.api.BeiKeApis;
import com.example.a83776.demo.model.http.api.DemoApi;
import com.example.a83776.demo.model.http.api.KoclaApis;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 15:10
 */
public class RetrofitHelper implements HttpHelper {
    private DemoApi mDemoApi;
    private KoclaApis mKoclaApis;
    private BeiKeApis mBeiKeApis;
    @Inject
    public RetrofitHelper(DemoApi demoApi, KoclaApis koclaApis, BeiKeApis beiKeApis) {
        this.mDemoApi = demoApi;
        this.mKoclaApis = koclaApis;
        this.mBeiKeApis = beiKeApis;
    }

    @Override
    public Flowable<Book> getSearchBook(String name, String tag, int start, int count) {
        return mDemoApi.getSearchBook(name, tag, start, count);
    }

    @Override
    public Flowable<WeiDianHttpResponse<List<Live>>> liveCourse(int type, int pageNo, int pageSize, String from, String channel, Integer studySection) {
        return mKoclaApis.liveCourse(type, pageNo, pageSize, from, channel, studySection);
    }

    @Override
    public Flowable<WeiDianHttpResponse<LiveDetail>> liveCourseDetail(String id) {
        return mKoclaApis.liveCourseDetail(id);
    }

    @Override
    public Flowable<LiveRoomInfo> huoQuZhiBoFangJianXinXi(String jiaZhangRuankoId, String erpDaKeBiaoKeCiUuid, String laoShiRuankoId) {
        return mBeiKeApis.huoQuZhiBoFangJianXinXi(jiaZhangRuankoId,erpDaKeBiaoKeCiUuid,laoShiRuankoId,1);
    }

    @Override
    public Flowable<LiveRoomInfo> huoQuZhiBoFangJian(String erpDaKeBiaoKeCiUuid, String laoShiRuankoId) {
        return mBeiKeApis.huoQuZhiBoFangJian(erpDaKeBiaoKeCiUuid,laoShiRuankoId);
    }
}
