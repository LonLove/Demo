package com.example.a83776.demo.dagger2test;

import dagger.Module;
import dagger.Provides;

/**
 * description:提供依赖对象
 * author: GaoJie
 * created at: 2018/5/30 17:32
 */
@Module
public class CarModule {
    public CarModule() {
    }

    @QualfierA
    @Provides
    public Engine provideEngineA() {//提供依赖对象的方法
        return new Engine("gearA");
    }

    @QualfierB
    @Provides
    public Engine provideEngineB() {
        return new Engine("gearB");
    }

//    @Provides
//    @CarScope
//    public Engine provideEngine() {
//        return new Engine("gear");
//    }
}
