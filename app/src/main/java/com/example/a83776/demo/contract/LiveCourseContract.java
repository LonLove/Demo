package com.example.a83776.demo.contract;

import com.example.a83776.demo.base.BasePresenter;
import com.example.a83776.demo.base.BaseView;
import com.example.a83776.demo.model.bean.Live;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 15:16
*/
public class LiveCourseContract {
    public interface View extends BaseView {
        void onLiveCourseResult(int code, String message, List<Live> lives);
    }

    public interface Presenter extends BasePresenter<View> {
        void liveCourse(int type, String from, String channel, Integer studySection);

        Disposable liveCourseTwo(int type, String from, String channel, Integer studySection);
    }
}
