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

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.incoder.mvc.manager.ConstantManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 网络请求进度条订阅.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 00:11
 */
public class ProgressSubscriber<T> extends DisposableObserver<T> implements ProgressDialogHandler.ProgressCancelListener {

    private SubscriberOnNextListener<T> mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isShowDialog = true;
    private Context mContext;

    public ProgressSubscriber(Context context) {
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, Context context, boolean isShowDialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = context;
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, isShowDialog);
    }

    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, Context context, boolean isShowDialog, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = context;
        this.isShowDialog = isShowDialog;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, isShowDialog);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
    }

    /**
     * 向观察者提供一个要观察的新项目
     *
     * @param t T
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 通知观察者错误信息
     *
     * @param t Throwable
     */
    @Override
    public void onError(Throwable t) {
        String error;

        if (!NetworkUtils.isAvailableByPing()) {
            error = "网络请求失败，请检查您的网络设置";
        } else if (t instanceof SocketTimeoutException) {
            error = "网络链接超时，请检查您的网络状态";
        } else if (t instanceof ConnectException) {
            error = "网络中断，请检查您的网络状态";
        } else if (t instanceof HttpException) {
            if (((HttpException) t).code() == ConstantManager.NOT_FOUND) {
                error = "未找到资源404";
            } else {
                error = "服务器终断，请稍后再试";
            }
        } else {
            if (TextUtils.isEmpty(t.getMessage())) {
                error = "系统错误";
            } else {
                error = t.getMessage();
            }
        }
        // 异常错误提示
        ToastUtils.showShort(error);
        // 取消进度动画
        dismissProgressDialog();
        // 取消下拉刷新
        dismissSwipeLayout();
    }

    /**
     * 通知观察者已完成发送基于推送的通知
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
        dismissSwipeLayout();
    }

    /**
     * 取消进度提示
     */
    @Override
    public void onCancelProgress() {
        if (!isDisposed()) {
            dispose();
        }
    }

    /**
     * 解散Swipe刷新
     */
    private void dismissSwipeLayout() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * SubscriberOnNextListener
     *
     * @param <T>
     */
    public interface SubscriberOnNextListener<T> {

        /**
         * 请求成功，进行业务处理
         *
         * @param t T
         */
        void onNext(T t);
    }
}
