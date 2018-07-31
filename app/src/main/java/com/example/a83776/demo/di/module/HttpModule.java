package com.example.a83776.demo.di.module;

import com.example.a83776.demo.BuildConfig;
import com.example.a83776.demo.app.Constants;
import com.example.a83776.demo.di.convert.fastjson.FastJsonConvertFactory;
import com.example.a83776.demo.di.convert.json.JsonConverterFactory;
import com.example.a83776.demo.di.convert.string.StringConverterFactory;
import com.example.a83776.demo.di.qualifier.BeikeUrl;
import com.example.a83776.demo.di.qualifier.DemoUrl;
import com.example.a83776.demo.di.qualifier.KoclaUrl;
import com.example.a83776.demo.model.http.api.BeiKeApis;
import com.example.a83776.demo.model.http.api.DemoApi;
import com.example.a83776.demo.model.http.api.KoclaApis;
import com.example.a83776.demo.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description:提供网络请求相关实例
 * author: GaoJie
 * created at: 2018/6/1 14:48
 */

/**
 * 空构造函数
 * App#getAppComponent中的newHttpModule()完成实例化
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    @DemoUrl
    public Retrofit providerDemoRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, DemoApi.HOST);
    }

    //demo服务器
    @Singleton
    @Provides
    public DemoApi providerDemoService(@DemoUrl Retrofit retrofit) {
        return retrofit.create(DemoApi.class);
    }
    @Singleton
    @Provides
    @KoclaUrl
    public Retrofit providerKoclaRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, KoclaApis.HOST);
    }

    //中心库服务器
    @Singleton
    @Provides
    public KoclaApis providerKoclaService(@KoclaUrl Retrofit retrofit) {
        return retrofit.create(KoclaApis.class);
    }
    @Singleton
    @Provides
    @BeikeUrl
    public Retrofit providerBeiKeRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BeiKeApis.HOST);
    }

    //中心库服务器
    @Singleton
    @Provides
    public BeiKeApis providerBeikeService(@BeikeUrl Retrofit retrofit) {
        return retrofit.create(BeiKeApis.class);
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    public OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//只打印网络请求
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印网络请求和返回结果
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);//设置请求的缓存文件路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);//缓存文件最大限制大小50M
        builder.addNetworkInterceptor(cacheInterceptor)//设置缓存 oKhttp只会对get请求进行缓存，post请求是不会进行缓存的(因为get请求的数据一般是比较持久的，而post一般是交互操作，没太大意义进行缓存)
                .addInterceptor(tokenInterceptor)
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重连
        return builder.build();
    }

    @Singleton
    @Provides
    public OkHttpClient.Builder provideOkhttpBuilder() {
        return new OkHttpClient.Builder();
    }

    //缓存(只对get有用,post没有缓冲)
    Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!SystemUtil.isNetworkConnected()) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存)
                request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (SystemUtil.isNetworkConnected()) {//有网络时，设置缓存超时时间0个小时 ,意思就是不读取缓存(数据不缓存，最大保存时长为0),或者有网一份钟失效，maxAge=60
                int maxAge = 0;
                response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .build();
            } else {
                //无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };
//拦截器添加tokenId
    private final Interceptor tokenInterceptor=new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!SystemUtil.isNetworkConnected()) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存)
            request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        String tokenId = "";
        //使用新的Token,创建新的请求
        request=request.newBuilder()
                .addHeader("tokenId", tokenId)
                .build();
        return chain.proceed(request);
    }
};
    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder.baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addConverterFactory(FastJsonConvertFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
