package com.example.a83776.demo.presenter;

import com.example.a83776.demo.base.RxPresenter;
import com.example.a83776.demo.contract.LiveCourseContract;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.http.response.WeiDianHttpResponse;
import com.example.a83776.demo.util.RxUtil;
import com.example.a83776.demo.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 15:22
 */
public class LiveCoursePresenter extends RxPresenter<LiveCourseContract.View> implements LiveCourseContract.Presenter {
    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 6 * 10;//每页6条*10页
    private int currentPage = 1;

    @Inject
    public LiveCoursePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void liveCourse(int type, String from, String channel, Integer studySection) {
        addSubscribe(mDataManager.liveCourse(type,currentPage++,NUM_OF_PAGE,from,channel,studySection)
        .compose(RxUtil.<WeiDianHttpResponse<List<Live>>>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<WeiDianHttpResponse<List<Live>>>(mView) {
            @Override
            public void onNext(WeiDianHttpResponse<List<Live>> response) {
                if (mView == null) {
                    return;
                }
                mView.onLiveCourseResult(response.getCode(),response.getMessage(),response.getData());
            }
        }));
    }

    /**
     *  刷新接口
     * @param type
     * @param from
     * @param channel
     * @param studySection
     * @return
     */
    @Override
    public Disposable liveCourseTwo(int type, String from, String channel, Integer studySection) {
        Disposable disposable= mDataManager.liveCourse(type, 1, NUM_OF_PAGE, from, channel, studySection)
        .compose(RxUtil.<WeiDianHttpResponse<List<Live>>>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<WeiDianHttpResponse<List<Live>>>(mView) {
            @Override
            public void onNext(WeiDianHttpResponse<List<Live>> response) {
                if (mView == null) {
                    return;
                }
                mView.onLiveCourseResult(response.getCode(), response.getMessage(), response.getData());
            }
        });
        return disposable;
    }
}
