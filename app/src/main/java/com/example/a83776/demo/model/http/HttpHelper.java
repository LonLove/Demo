package com.example.a83776.demo.model.http;

import com.example.a83776.demo.model.bean.Book;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * description:网络请求
 * author: GaoJie
 * created at: 2018/6/20 15:00
 */
public interface HttpHelper {
    Flowable<Book> getSearchBook(String name, String tag, int start, int count);

    Flowable<WeiDianHttpResponse<List<Live>>> liveCourse(int type,int pageNo,int pageSize,String from,String channel,Integer studySection);

    Flowable<WeiDianHttpResponse<LiveDetail>> liveCourseDetail(String id);

    Flowable<LiveRoomInfo> huoQuZhiBoFangJianXinXi(String jiaZhangRuankoId, String erpDaKeBiaoKeCiUuid, String laoShiRuankoId);

    Flowable<LiveRoomInfo> huoQuZhiBoFangJian(String erpDaKeBiaoKeCiUuid, String laoShiRuankoId);
}
