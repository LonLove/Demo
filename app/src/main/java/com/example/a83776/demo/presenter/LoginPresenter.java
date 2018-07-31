package com.example.a83776.demo.presenter;

import com.example.a83776.demo.base.RxPresenter;
import com.example.a83776.demo.contract.Logincontract;
import com.example.a83776.demo.model.DataManager;
import com.example.a83776.demo.model.bean.Book;
import com.example.a83776.demo.util.RxUtil;
import com.example.a83776.demo.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/26 15:32
 */
public class LoginPresenter extends RxPresenter<Logincontract.View> implements Logincontract.Presenter {

    private  DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager mDateManager) {
        mDataManager = mDateManager;
    }

    @Override
    public void getSearchBook(String name, String tag, int start, int count) {
        addSubscribe(mDataManager.getSearchBook(name, tag, start, count)
                .compose(RxUtil.<Book>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Book>(mView) {
                    @Override
                    public void onNext(Book book) {
                        if (mView == null) {
                            //                           LogUtils.d("loginpres",book.toString());
                            return;
                        }
                        if (book != null) {
                            mView.onGetBookResult(book.getCode(),book.getMessage(),book.getBooks());
                        }
                    }
                }));
    }

//    @Override
//    public void onCreate() {
//        mDateManager = new DateManager(mContext);
//    }


}
