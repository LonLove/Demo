package com.example.a83776.demo.model.http.exception;
/**
 * description:
 * author: GaoJie
 * created at: 2018/5/26 16:14
*/
public class ApiException extends Exception{
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }
}
