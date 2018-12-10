package com.annis.baselib.base.http;


import com.annis.baselib.base.http.JsonFormat.JsonFormat;
import com.annis.baselib.utils.LogUtils;
import com.annis.baselib.utils.TokenUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class HttpProvider {
    private static final String TOKEN_NAME = "jzjc-token";
    private static final String HEADER_USERID = "userId";
    private static final String TAG = "HTTP";
    private static OkHttpClient client = null;

    public static Retrofit getRetrofit(String baseUrl) {
        return getRetrofitBuilder(baseUrl).client(getHttpClient(provideOkHttpBuilder())).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(JsonFormat.getGson())).build();
    }

    private static Retrofit.Builder retrofitBuilder;

    private static OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    private static Retrofit.Builder getRetrofitBuilder(String url) {
        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder();
        }
        retrofitBuilder.baseUrl(url);
        return retrofitBuilder;
    }

    private static OkHttpClient getHttpClient(OkHttpClient.Builder builder) {
        if (client != null) return client;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LogHelper());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        Interceptor apikey = chain -> {
            Request request = chain.request();
            request = request.newBuilder().addHeader(TOKEN_NAME, TokenUtils.getToken()).addHeader(HEADER_USERID, TokenUtils.getUserId()).build();
            return chain.proceed(request);
        };
        //设置统一的请求头部参数
        builder.addInterceptor(apikey);

        //builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        client = builder.build();
        return client;
    }

    private static class LogHelper implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String s) {
            LogUtils.i(TAG, s);
        }
    }
}
