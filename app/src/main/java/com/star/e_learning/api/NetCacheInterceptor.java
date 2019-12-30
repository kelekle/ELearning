package com.star.e_learning.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);

        //设置响应的缓存时间为60秒，即设置Cache-Control头，并移除pragma消息头，因为pragma也是控制缓存的一个消息头属性
        originResponse = originResponse.newBuilder()
                .removeHeader("pragma")
                .header("Cache-Control", "max-age=30")
                .build();

        return originResponse;
    }
}
