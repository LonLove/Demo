package com.example.a83776.demo.dagger2test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * description: 限定作作用域，实现局部单例
 * author: GaoJie
 * created at: 2018/5/31 14:16
*/
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CarScope {
}
