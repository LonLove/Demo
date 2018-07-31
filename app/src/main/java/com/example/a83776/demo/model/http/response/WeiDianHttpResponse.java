package com.example.a83776.demo.model.http.response;
/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 14:47
*/
public class WeiDianHttpResponse<T> {
    private int code;
    private String message;
    private String msg;
    private T data;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
