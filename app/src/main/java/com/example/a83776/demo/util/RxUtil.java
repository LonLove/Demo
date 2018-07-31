package com.example.a83776.demo.util;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description: Rx工具类
 * author: GaoJie
 * created at: 2018/5/26 15:46
*/
public class RxUtil {
    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T>FlowableTransformer<T,T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建Flowable
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return  Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        },BackpressureStrategy.BUFFER);//背压
    }
}
