package com.example.a83776.demo.di.convert.fastjson;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class FastJsonRequestBodyConvert<T> implements Converter<T, RequestBody> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONString(value).getBytes(UTF_8));
    }
}