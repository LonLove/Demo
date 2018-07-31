package com.example.a83776.demo.presenter;

import com.example.a83776.demo.base.RxPresenter;
import com.example.a83776.demo.contract.LiveCourseDetailContract;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.bean.LiveDetail;
import com.example.a83776.demo.model.bean.LiveRoomInfo;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;
import com.example.a83776.demo.util.RxUtil;
import com.example.a83776.demo.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/30 10:11
 */
public class LiveCourseDetailPresenter extends RxPresenter<LiveCourseDetailContract.View> implements LiveCourseDetailContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public LiveCourseDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void liveDetail(String id) {
        addSubscribe(mDataManager.liveCourseDetail(id)
                .compose(RxUtil.<WeiDianHttpResponse<LiveDetail>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<WeiDianHttpResponse<LiveDetail>>(mView) {
                    @Override
                    public void onNext(WeiDianHttpResponse<LiveDetail> response) {
                        if (mView == null) {
                            return;
                        }
                        mView.onLiveDetailResult(response.getCode(), response.getMessage(), response.getData());
                    }
                }));
    }

    @Override
    public void getScheduleLiveRoomInfo(String erpDaKeBiaoKeCiUuid, String laoShiRuankoId) {
        addSubscribe(mDataManager.huoQuZhiBoFangJian(erpDaKeBiaoKeCiUuid, laoShiRuankoId)
                .compose(RxUtil.<LiveRoomInfo>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LiveRoomInfo>(mView) {
                    @Override
                    public void onNext(LiveRoomInfo response) {
                        if (mView == null) {
                            return;
                        }
                        mView.onScheduleLiveRoomInfoResult(response.getCode(), response.getMessage(), response);
                    }
                }));
    }
}
