/*
 * Copyright (C) 2018 The Jerry xu Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.incoder.mvc.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.incoder.mvc.BuildConfig;
import org.incoder.mvc.manager.ConstantManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 00:03
 */
public class RetrofitManager {

    private static final long CONNECT_TIME = 10000;
    private Retrofit.Builder retrofit;
    private OkHttpClient client;
    private ApiService apiService;
    private Class apiClass;
    /**
     * 设置GSON的非严格模式setLenient()
     */
    private final Gson mGson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitManager() {

    }

    /**
     * 获取单例
     *
     * @return retrofit
     */
    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = createApi(ApiService.class);
        }
        return apiService;
    }

    private static <T> T createApi(Class<T> c) {
        return RetrofitManager.getInstance().getRetrofit().create(c);
    }

    public static <T> T createApi(Class<T> c, String api) {
        return RetrofitManager.getInstance().getRetrofit(api).create(c);
    }

    private Retrofit.Builder getBuilder() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder();
                }
            }
        }
        return retrofit;
    }

    /**
     * OkHttp
     *
     * @return OkHttpClient
     */
    private OkHttpClient getClient() {
        if (client == null) {
            synchronized (RetrofitManager.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
//                            .cache(new Cache(new File(ConstantManager.CACHE_PATH), 1024 * 1024 * 50))
                            // 方法为设置出现错误进行重新连接。
                            .retryOnConnectionFailure(true)
                            // 网络请求日志
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ?
                                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                            // 网络请求连接器（添加通用参数）
                            .addNetworkInterceptor(new InterceptorHelper.HeadInterceptor())
                            // 设置链接超时时间
                            .connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
                            // 设置写入超时时间
                            .writeTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
                            // 设置读取超时时间
                            .readTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
                            .build();
                }
            }
        }
        return client;
    }

    /**
     * 动态传入请求url前缀
     *
     * @param api url前缀
     * @return Retrofit
     */
    private Retrofit getRetrofit(String api) {
        return getBuilder()
                .baseUrl(api)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    /**
     * 默认API前缀
     *
     * @return Retrofit
     */
    private Retrofit getRetrofit() {
        return getBuilder()
                .baseUrl(ConstantManager.BASE_API)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    /**
     * 线程控制调度
     *
     * @param o   Observable：被观察者在 子线程 中生产事件（如实现耗时操作等等）
     * @param s   Observer： 观察者在 主线程 接收 & 响应事件（即实现UI操作）
     * @param <T> 1. 整体方法调用顺序：观察者.onSubscribe（）> 被观察者.subscribe（）> 观察者.doOnNext（）>观察者.onNext（）>观察者.onComplete()
     *            2. 观察者.onSubscribe（）固定在主线程进行
     */
    public <T> void toSubscribe(Observable<T> o, Observer<T> s) {
        // Schedulers.immediate()：当前线程 = 不指定线程；默认操作
        // AndroidSchedulers.mainThread()：Android主线程；常用来操作UI线程
        // Schedulers.newThread()：常规新线程；常用来耗时等操作
        // Schedulers.io()：io操作线程；常用来网络请求，读写文件等io密集型操作
        // Schedulers.computation()：CPU计算操作线程；常用来进行大量计算操作

        // 指定被观察者 生产事件的线程
        o.subscribeOn(Schedulers.io())
                // 指定观察者 接收 & 响应事件的线程
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅（subscribe）连接观察者和被观察者（表示观察者对被观察者发送的任何事件都作出响应）
                .subscribe(s);
    }

}
