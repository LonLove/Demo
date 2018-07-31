package com.example.a83776.demo.model.http.api;

import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/28 15:04
*/
public interface KoclaApis {
    String HOST = ApiConfig.BASE_KOCLA;
    /**
     *
     * @param type 类型 Integer 是 0：直播课列表; 1:关注列表
     * @param pageNo 当前页码 Integer 是
     *  tokeId String 否 在请求头中传入
     * @param pageSize
     * @param from App来源 String 是 壹家教 ：oneHour, 贝贝 ：beiBei,酷客：kuKe,电视版，HD
     * @param channel   渠道 String 否 步步高渠道：bbg
     * @param studySection 学段 Integer 否
     * 查询直播课列表，登陆时需要在请求头传入tokenId， type为0查询直播课列表，type为1查询关注列表， pageNo为当前页码，后台默认每页10条数据
     * @return
     */
    @GET("course/live/v1/liveCourse/attention/{type}/num/{pageNo}/app/{from}")
    Flowable<WeiDianHttpResponse<List<Live>>> liveCourse(
            @Path("type") int type,
            @Path("pageNo") int pageNo,
            @Header("pageSize") int pageSize,
            @Path("from") String from,
            @Query("channel") String channel,
            @Query("studySection") Integer studySection);

    /**
     * 获取直播课详情
     * @param id
     * @return
     */
    @GET("course/live/v1/liveCourse/{id}")
    Flowable<WeiDianHttpResponse<LiveDetail>> liveCourseDetail(@Path("id") String id);
}
