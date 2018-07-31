package com.example.a83776.demo.di.convert.string;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/1 17:02
*/
public class StringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {
                @Override
                public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, value);
                }
            };
        }
        return null;
    }

//    @Override
//    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//        if (String.class.equals(type)) {
//            return new Converter<ResponseBody, String>() {
//                @Override
//                public String convert(ResponseBody value) throws IOException {
//                    return value.string();
//                }
//            };
//        }
//        return null;
//    }
//
//    @Override
//    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
//        if (String.class.equals(type)) {
//            return new Converter<String, RequestBody>() {
//                @Override
//                public RequestBody convert(String value) throws IOException {
//                    return RequestBody.create(MEDIA_TYPE, value);
//                }
//            };
//        }
//        return null;
//    }
}
