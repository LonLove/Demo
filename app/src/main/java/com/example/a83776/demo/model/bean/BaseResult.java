package com.example.a83776.demo.model.bean;
/**
 * description: 实体类基类（用一句话描述该文件做什么）
 * author: GaoJie
 * created at: 2018/6/20 16:45
*/
public class BaseResult {
    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
