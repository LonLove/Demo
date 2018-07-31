package com.example.a83776.demo.dagger2test;

import dagger.Component;

/**
 * description: 注入器，将engine注入到Car中
 * author: GaoJie
 * created at: 2018/5/30 16:54
*/
@CarScope
@Component(modules = CarModule1.class)//告诉dagger2提供依赖的是CarComponent类
public interface CarComponent1 {
    void inject(Car1 car);
}
