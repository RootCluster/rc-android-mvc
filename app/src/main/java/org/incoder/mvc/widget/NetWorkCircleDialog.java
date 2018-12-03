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

package org.incoder.mvc.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import org.incoder.mvc.R;

/**
 * NetWorkCircleDialog.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 01:14
 */
public class NetWorkCircleDialog extends Dialog {

    private Context mContext;
    private ProgressBar mProgressBar;

    public NetWorkCircleDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        // setting dialog view
        View view = View.inflate(context, R.layout.dialog_progress_circle, null);
        setContentView(view);
        mProgressBar = view.findViewById(R.id.pb_network);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void show() {
        if (mContext != null && !this.isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (mContext != null) {
            super.dismiss();
        }
    }

    @Override
    public void cancel() {
        if (mContext != null) {
            super.cancel();
        }
    }
}
