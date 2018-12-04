package org.incoder.mvc.retrofit;

import org.incoder.mvc.manager.ConstantManager;
import org.incoder.mvc.model.GlobalResponse;

import io.reactivex.functions.Function;

/**
 * ResultFunction.
 *
 * @author : Jerry xu    date : 2018/12/4  14:03
 * @version : 1.0.0
 */
public class ResultFunction<T> implements Function<GlobalResponse<T>, T> {

    public ResultFunction() {

    }

    @Override
    public T apply(GlobalResponse<T> response) throws Exception {
        if (response.getCode() != ConstantManager.REQUEST_SUCCESS) {
            throw new ApiException();
        }
        return response.getResults();
    }
}
