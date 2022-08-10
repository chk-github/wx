package com.chk.wx.interceptor;

import cn.hutool.json.JSONObject;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * Forest拦截器获取响应对象(暂无用)
 * 学习地址 https://forest.dtflyx.com/docs/adv/interceptor
 * @author chk
 * @since 2022/4/15 14:21
 */
@Slf4j
public class ForestInterceptor implements Interceptor<JSONObject> {
    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
    }

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest request, ForestResponse response) {
        log.error("请求微信接口发生异常或请求失败");
        ex.fillInStackTrace();
    }
}
