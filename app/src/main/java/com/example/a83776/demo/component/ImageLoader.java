package com.example.a83776.demo.component;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.a83776.demo.app.App;

/**
 * 加载图片
 * 外存缓存机制：
 * DiskCacheStrategy.NONE： 表示不缓存任何内容。
 * DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
 * DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
 * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片
 * description:
 * author: GaoJie
 * created at: 2018/6/29 9:11
 */
public class ImageLoader {
    private final static RequestOptions requestOptions = RequestOptions
            .priorityOf(Priority.LOW)//指定加载的优先级，优先级越高越优先加载，但不保证所有图片都按序加载
            .sizeMultiplier(0.5f)//加载资源之前给Target大小设置系数
            .centerCrop()
            .skipMemoryCache(false)//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.ALL);//磁盘缓存
    public static void load(Context context, @DrawableRes int resid, ImageView iv) {
        if (!App.getAppComponent().preferencesHelper().getNoImageState()) {
            Glide.with(context)
                    .load(resid)
                    //.thumbnail(0.1f)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .apply(requestOptions.placeholder(resid))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!App.getAppComponent().preferencesHelper().getNoImageState()) {
            //Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            Glide.with(context)
                    .load(url)
                    //.thumbnail(0.1f)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .apply(requestOptions.placeholder(0))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    public static void load(Context context, String url, ImageView iv, int holder) {    //不缓存，全部从网络加载
        //Glide.with(context).load(url).placeholder(holder).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        Glide.with(context)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).placeholder(holder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(requestOptions.placeholder(holder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }

    public static void loadWithCache(Context context, String url, ImageView iv, int holder) {    //缓存 SD
        //Glide.with(context).load(url).placeholder(holder).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        Glide.with(context)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).placeholder(holder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .apply(requestOptions.placeholder(holder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }

    public static void load(android.support.v4.app.Fragment fragment, String url, ImageView iv, int holder) {    //不缓存，全部从网络加载
        //Glide.with(fragment).load(url).priority(Priority.LOW).placeholder(holder).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        Glide.with(fragment)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).placeholder(holder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(requestOptions.placeholder(holder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }

    public static void loadDontAnimate(Fragment fragment, String url, ImageView iv, int holder) {    //不缓存，全部从网络加载
        Glide.with(fragment)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).placeholder(holder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(requestOptions.placeholder(holder))
                .into(iv);
    }

    public static void load(Fragment fragment, String url, ImageView iv) {    //不缓存，全部从网络加载
        //Glide.with(fragment).load(url).priority(Priority.LOW).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        Glide.with(fragment)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(requestOptions.placeholder(0))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }

    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        boolean isDestroyed;
        if (Build.VERSION.SDK_INT >= 17) {
            isDestroyed = activity.isDestroyed();
        } else {
            isDestroyed = activity.isFinishing();
        }
        if (!isDestroyed) {
            //Glide.with(activity).load(url).priority(Priority.LOW).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            Glide.with(activity)
                    .load(url)
                    //.thumbnail(0.1f)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .apply(requestOptions.placeholder(0))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    public static void load(Activity activity, String url, ImageView iv, Drawable holder) {    //使用Glide加载圆形ImageView(如头像)时
        boolean isDestroyed;
        if (Build.VERSION.SDK_INT >= 17) {
            isDestroyed = activity.isDestroyed();
        } else {
            isDestroyed = activity.isFinishing();
        }
        if (!isDestroyed) {
            Glide.with(activity)
                    .load(url)
                    //.thumbnail(0.1f)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).placeholder(holder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .apply(requestOptions.placeholder(holder))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    public static void load(Activity activity, String url, ImageView iv, @DrawableRes int holder) {    //使用Glide加载圆形ImageView(如头像)时
        boolean isDestroyed;
        if (Build.VERSION.SDK_INT >= 17) {
            isDestroyed = activity.isDestroyed();
        } else {
            isDestroyed = activity.isFinishing();
        }
        if (!isDestroyed) {
            //Glide.with(activity).load(url).priority(Priority.LOW).placeholder(holder).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            //Glide.with(activity).load(url).priority(Priority.LOW).placeholder(holder).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            Glide.with(activity)
                    .load(url)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).error(holder).placeholder(holder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .apply(requestOptions.placeholder(holder))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        //Glide.with(context).load(url).priority(Priority.LOW).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        Glide.with(context)
                .load(url)
                //.thumbnail(0.1f)
                //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(requestOptions.placeholder(0))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }


    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        boolean isDestroyed;
        if (Build.VERSION.SDK_INT >= 17) {
            isDestroyed = activity.isDestroyed();
        } else {
            isDestroyed = activity.isFinishing();
        }
        if (!isDestroyed) {
            //Glide.with(activity).load(url).priority(Priority.LOW).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            Glide.with(activity)
                    .load(url)
                    //.thumbnail(0.1f)
                    //.apply(RequestOptions.priorityOf(Priority.LOW).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .apply(requestOptions.placeholder(0))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }
}
