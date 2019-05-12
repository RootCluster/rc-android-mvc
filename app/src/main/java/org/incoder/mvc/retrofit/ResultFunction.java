package org.incoder.mvc.retrofit;

import org.incoder.mvc.model.BaseResponse;

import io.reactivex.functions.Function;

/**
 * ResultFunction.
 *
 * @author : Jerry xu
 * @date : 2018/12/4  14:03
 */
public class ResultFunction<T> implements Function<BaseResponse<T>, T> {

    public ResultFunction() {

    }

    @Override
    public T apply(BaseResponse<T> response) throws Exception {
        // 这里对返回的数据进行全局处理
        if (response.isError()) {
            throw new ApiException();
        }
        return response.getResults();
    }
}
