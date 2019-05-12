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

import org.incoder.mvc.model.BaseResponse;
import org.incoder.mvc.model.DoubanMovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 应用API接口.
 *
 * @author : Jerry xu
 * @date : 2018/12/4 00:01
 */
public interface ApiService {

    /**
     * 获取豆瓣电影的Top250
     *
     * @param start start
     * @param count count
     * @return DoubanMovieResponse
     */
    @GET("top250")
    Observable<BaseResponse<DoubanMovieResponse>> getTopMovie(@Query("start") int start, @Query("count") int count);


    @GET("top250")
    Observable<DoubanMovieResponse> getTopMovie(@Query("start") int star);
}
