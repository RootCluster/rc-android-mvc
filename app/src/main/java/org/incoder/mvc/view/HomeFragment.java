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

package org.incoder.mvc.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.incoder.mvc.R;
import org.incoder.mvc.base.BaseFragmentV4;
import org.incoder.mvc.base.IBaseView;
import org.incoder.mvc.model.DoubanMovieResponse;
import org.incoder.mvc.retrofit.ApiService;
import org.incoder.mvc.retrofit.ProgressSubscriber;
import org.incoder.mvc.retrofit.ResultFunction;
import org.incoder.mvc.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * HomeFragment.
 *
 * @author : Jerry xu
 * @date : 2018/12/3 22:27
 */
public class HomeFragment extends BaseFragmentV4 implements IBaseView {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        RetrofitManager.getInstance().toSubscribe(
                RetrofitManager.createApi(ApiService.class).getTopMovie(1, 1)
                        .map(new ResultFunction<>()),
                new ProgressSubscriber<>(o -> {
                    // 业务处理


                }, getContext()));

        RetrofitManager.getInstance().toSubscribe(
                RetrofitManager.createApi(ApiService.class).getTopMovie(1)
                        .map(new Function<DoubanMovieResponse, List<DoubanMovieResponse.SubjectsBean>>() {
                            @Override
                            public List<DoubanMovieResponse.SubjectsBean> apply(DoubanMovieResponse t) throws Exception {
                                // 做特殊的数据返回校验比对，比对成功后返回业务需要处理的对象
                                return t.getSubjects();
                            }
                        })
                , new ProgressSubscriber<>(new ProgressSubscriber.SubscriberOnNextListener<List<DoubanMovieResponse.SubjectsBean>>() {
                    @Override
                    public void onNext(List<DoubanMovieResponse.SubjectsBean> doubanMovieResponse) {
                        // 业务处理：得到数据比对后真正用于业务处理的数据对象

                    }
                }, getContext())
        );
    }

    @Override
    public boolean isNeedEventBus() {
        return false;
    }
}
