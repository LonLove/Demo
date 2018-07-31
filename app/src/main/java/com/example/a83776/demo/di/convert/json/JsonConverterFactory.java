package com.example.a83776.demo.di.convert.json;


import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
/**
 * description:
 * author: GaoJie
 * created at: 2018/6/1 17:03
*/
public class JsonConverterFactory extends Converter.Factory {
    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (JSONObject.class.equals(type)) {
            return new JsonResponseBodyConverter<JSONObject>();
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (JSONObject.class.equals(type)) {
            return new JsonRequestBodyConverter<JSONObject>();
        }
        return null;
    }

//    @Override
//    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//        if (JSONObject.class.equals(type)) {
//            return new JsonResponseBodyConverter<JSONObject>();
//        }
//        return null;
//    }
//
//    @Override
//    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
//        if (JSONObject.class.equals(type)) {
//            return new JsonRequestBodyConverter<JSONObject>();
//        }
//        return null;
//    }
}