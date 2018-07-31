package com.example.a83776.demo.dagger2test;

/**
 * description: 依赖提供方
 * author: GaoJie
 * created at: 2018/5/30 16:51
 */
public class Engine1 {
    private String gear;

    public Engine1(String gear) {
        System.out.println("create engine");
        this.gear = gear;
    }

}
