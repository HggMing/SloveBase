package com.ming.slove.base.api.service;

import android.content.Context;

import com.ming.slove.base.app.APP;
import com.ming.slove.base.app.APPS;
import com.ming.slove.base.common.utils.BaseTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ming.slove.base.app.APPS.BASE_URL;


/**
 * 服务器连接客服端
 */
public class MyServiceClient {
    private static MyService mService;
    private static OkHttpClient mClient;

    public static MyService getService() {
        if (mService == null) {
            createService();
        }
        return mService;
    }

    private static void createService() {
        mService = createRetrofit().create(MyService.class);
    }


    private static Retrofit createRetrofit() {
        if (mClient == null) {
            createOkHttp();
        }
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    private static void createOkHttp() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Context mContext = APP.getInstance().getApplicationContext();
                Request request = chain.request();//拦截reqeust
                if (!BaseTools.checkNetWorkStatus(mContext)) {//判断网络连接状况
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                            .build();
                }

                Response response = chain.proceed(request);
                if (BaseTools.checkNetWorkStatus(mContext)) {
                    int maxAge = 60 * 60;// 有网络时 设置缓存超时时间1个小时
                    response.newBuilder()
                            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//设置缓存策略，及超时策略
                            .build();
                }
                return response;
            }
        };

        File cacheDirectory = new File(APPS.FILE_PATH_HTTPCACHE);
        Cache cache = new Cache(cacheDirectory, 60 * 1024 * 1024);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设定10秒超时
                .addInterceptor(interceptor)
                .cache(cache)//设置缓存目录
                .build();
    }
}




