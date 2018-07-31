package com.example.a83776.demo.dagger2test;

import javax.inject.Inject;

/**
 * description:需求依赖方，依赖engine类
 * author: GaoJie
 * created at: 2018/5/30 16:43
 */

public class Car {
    @QualfierA
    @Inject
    Engine mEngineA;//添加@Inject告诉dagger2为自己提供依赖
    @QualfierB
    @Inject
    Engine mEngineB;

    public Car() {//
        DaggerCarComponent
                .builder()
                .carModule(new CarModule())
                .build()
                .inject(this);
    }

    public Engine getEngineA() {
        return this.mEngineA;
    }

    public Engine getEngineB() {
        return this.mEngineB;
    }
}
