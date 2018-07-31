package com.example.a83776.demo.contract;

import com.example.a83776.demo.base.BasePresenter;
import com.example.a83776.demo.base.BaseView;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/29 17:31
*/
public interface LiveCourseDetailContract {
    public interface View extends BaseView {
        void onLiveDetailResult(int code, String message, LiveDetail lives);
        void onScheduleLiveRoomInfoResult(int code, String message, LiveRoomInfo response);
    }

    public interface Presenter extends BasePresenter<View> {
        void liveDetail(String id);
        void getScheduleLiveRoomInfo(String erpDaKeBiaoKeCiUuid, String laoShiRuankoId);
    }
}
