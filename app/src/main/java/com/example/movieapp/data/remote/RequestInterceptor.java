package com.example.movieapp.data.remote;

import com.example.movieapp.Commons.ApiKey;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();

        HttpUrl newUrl = originalUrl.newBuilder()
                        .addQueryParameter("api_key", ApiKey.api_key)
                        .build();
        Request newRequest = originalRequest.newBuilder()
                            .url(newUrl)
                            .build();
        return chain.proceed(newRequest);
    }
}
