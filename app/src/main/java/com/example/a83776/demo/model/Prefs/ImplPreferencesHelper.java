package com.example.a83776.demo.model.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a83776.demo.app.App;
import com.example.a83776.demo.app.Constants;

import javax.inject.Inject;

/**
 * description: sp实现类
 * author: GaoJie
 * created at: 2018/6/29 10:43
 */
public class ImplPreferencesHelper implements PreferencesHelper {
    private static final boolean DEFAULT_NO_IMAGE = false;
    private final SharedPreferences mSp;
    private static final String SHAREPREFERENCES_NAME = "my_sp";
    @Inject
    public ImplPreferencesHelper() {
        mSp = App.getInstance().getSharedPreferences(SHAREPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getNoImageState() {
        return mSp.getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }
}
