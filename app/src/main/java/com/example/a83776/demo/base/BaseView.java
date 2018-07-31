package com.example.a83776.demo.base;

/**
 * description: view基类
 * author: GaoJie
 * created at: 2018/5/25 16:20
 */
public interface BaseView {
    void showErrorMsg(String msg);

    /**
     * state
     */
    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
