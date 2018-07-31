package com.example.a83776.demo.dagger2test;

import com.example.a83776.demo.util.LogUtils;

/**
 * description: 依赖提供方
 * author: GaoJie
 * created at: 2018/5/30 16:51
*/
public class Engine {
    private String gear;
    public Engine(String gear) {
        this.gear = gear;
    }

    public void printGearName() {
        LogUtils.d("GearName",gear);
    }
}
