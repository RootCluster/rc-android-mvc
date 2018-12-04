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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.incoder.mvc.inter.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragmentV4.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 00:14
 */
public abstract class BaseFragmentV4 extends Fragment implements IBaseView {

    private Unbinder mUnbinder;
    private View mContentView;
    protected Activity mActivity;
    private boolean isNeedEventBus;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft;
            if (getFragmentManager() != null) {
                ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setBaseView(inflater, bindLayout());
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        mContentView = inflater.inflate(layoutId, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        // 绑定到butterKnife
        mUnbinder = ButterKnife.bind(mContentView);
        if (isNeedEventBus()) {
            isNeedEventBus = true;
            //注册事件
            EventBus.getDefault().register(this);
        }
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onDestroyView() {
        if (mContentView != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // 解绑ButterKnife
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        // 取消事件注册
        if (isNeedEventBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

}
