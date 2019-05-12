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

package org.incoder.mvc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * IBaseView.
 *
 * @author : Jerry xu
 * @date : 2018/12/4 00:15
 */
public interface IBaseView {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@Nullable final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();

    /**
     * 是否需要订阅事件
     *
     * @return boolean
     */
    boolean isNeedEventBus();

    /**
     * 初始化 view
     *
     * @param savedInstanceState savedInstanceState
     * @param contentView        contentView
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();
}
