package com.example.a83776.demo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a83776.demo.R;
import com.example.a83776.demo.app.App;

/**
 * description:
 * author: GaoJie
 * created at: 2018/7/2 17:42
 */
public class ToastUtil {
    static ToastUtil td;
    Context mContext;
    Toast mToast;
    String msg;

    public ToastUtil(Context context) {
        mContext = context;
    }

    public static void show(int resId) {
        longShow(App.getInstance().getString(resId));
    }

    public static void longShow(String msg) {
        if (td == null) {
            td = new ToastUtil(App.getInstance());
        }
        td.setText(msg);
        td.createLong().show();
    }

    public static void shortShow(String msg) {
        if (td == null) {
            td = new ToastUtil(App.getInstance());
        }
        td.setText(msg);
        td.createShort().show();
    }

    public Toast createLong() {
        View contentView = View.inflate(mContext, R.layout.dialog_toast, null);
        TextView textView = contentView.findViewById(R.id.toast_msg);
        mToast = new Toast(mContext);
        mToast.setView(contentView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        textView.setText(msg);
        return mToast;
    }

    public Toast createShort() {
        View contentView = View.inflate(mContext, R.layout.dialog_toast, null);
        TextView textView = contentView.findViewById(R.id.toast_msg);
        mToast = new Toast(mContext);
        mToast.setView(contentView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        textView.setText(msg);
        return mToast;
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    private void setText(String msg) {
        this.msg = msg;
    }
}
