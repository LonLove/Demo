package com.example.a83776.demo.dagger2test;

import javax.inject.Inject;

/**
 * description:需求依赖方，依赖engine类
 * author: GaoJie
 * created at: 2018/5/30 16:43
 */

public class Car1 {

    public Engine1 getEngineA() {
        return mEngineA;
    }

    public Engine1 getEngineB() {
        return mEngineB;
    }

    @Inject
    Engine1 mEngineA;
    @Inject
    Engine1 mEngineB;

    public Car1() {//
        DaggerCarComponent1
                .builder()
                .carModule1(new CarModule1())
                .build()
                .inject(this);
    }

}
