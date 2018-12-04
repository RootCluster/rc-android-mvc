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

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求拦截器.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 00:03
 */
class InterceptorHelper {

    /**
     * Header拦截器
     */
    public static class HeadInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    // 设备类型
//                    .header(RetrofitManager.platform, "android")
                    // 用户token，从文件中获取
//                    .header(RetrofitManager.token, SPUtils.getInstance().getString(ConstantManager.SP_TOKEN))
                    .build();
            return chain.proceed(authorised);
        }
    }
}
