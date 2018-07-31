package com.example.a83776.demo.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/20 18:09
*/
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
