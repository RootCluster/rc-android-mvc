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

/**
 * MineFragment.
 *
 * @author : Jerry xu
 * @date : 2018/12/3 22:27
 */
public class MineFragment extends BaseFragmentV4 implements IBaseView {

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public boolean isNeedEventBus() {
        return false;
    }
}
