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
import android.os.Handler;
import android.os.RecoverySystem;

import org.incoder.mvc.widget.NetWorkCircleDialog;

/**
 * 网络请求进度条处理.
 *
 * @author : Jerry xu
 * @since : 2018/12/4 00:12
 */
public class ProgressDialogHandler extends Handler {

    private static final int SHOW_PROGRESS_DIALOG = 1;
    private static final int DISMISS_PROGRESS_DIALOG = 2;

    private NetWorkCircleDialog mCircleDialog;

    private Context mContext;
    private boolean cancelable;
    private RecoverySystem.ProgressListener mListener;

}
