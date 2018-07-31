package com.example.a83776.demo.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * description:SnackBar是加强版的Toast，或者是一个轻量级的Dialog。
 * author: GaoJie
 * created at: 2018/6/25 15:40
 */
public class SnackBarUtil {
    public static void showLong(View view,String msg) {
        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show();
    }
    public static  void  showShort(View view,String msg) {
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
    }
}
