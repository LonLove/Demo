package com.example.a83776.demo.model.http.api;

import com.example.a83776.demo.model.bean.Book;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * description:demo接口
 * author: GaoJie
 * created at: 2018/5/26 14:31
*/
public interface DemoApi {
   String HOST = ApiConfig.BASE_DEMO;
   /**
    *
    * @param name
    * @param tag
    * @param start
    * @param count
    * @return
    */
   @GET("book/search")
   Flowable<Book> getSearchBook(@Query("q")String name,@Query("tag")String tag,@Query("start")int start,@Query("count")int count);


}
