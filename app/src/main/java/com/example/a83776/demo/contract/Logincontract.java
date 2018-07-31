package com.example.a83776.demo.contract;

import com.example.a83776.demo.base.BasePresenter;
import com.example.a83776.demo.base.BaseView;
import com.example.a83776.demo.model.bean.Book;

import java.util.List;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/26 9:28
*/
public class Logincontract {
    public interface View extends BaseView{
//        void onSuccess(Book mBook);
//        void onError(String result);

        void onGetBookResult(int code, String message, List<Book.BooksBean> books);
    }

    public interface Presenter extends BasePresenter<View>{
        void getSearchBook(String name,String tag,int start,int count);
    }
}
