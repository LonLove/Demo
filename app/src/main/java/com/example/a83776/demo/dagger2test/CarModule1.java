package com.example.a83776.demo.dagger2test;

import dagger.Module;
import dagger.Provides;

/**
 * description:提供依赖对象
 * author: GaoJie
 * created at: 2018/5/30 17:32
 */
@Module
public class CarModule1 {
    public CarModule1() {
    }
    @Provides
    @CarScope
    public Engine1 provideEngine() {
        return new Engine1("gear");
    }
}
