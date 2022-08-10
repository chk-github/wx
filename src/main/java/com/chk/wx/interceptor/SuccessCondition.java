package com.chk.wx.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.callback.SuccessWhen;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.stereotype.Component;

/**
 * MySuccessCondition
 * 自定义Wxclient请求成功失败条件
 * @author chk
 * @since 2022/4/15 15:29
 */
@Component
public class SuccessCondition implements SuccessWhen {
    /**
     * 满足条件则成功否则都是失败
     * @author chk
     * @date 2022/4/15 15:31
     **/
    @Override
    public boolean successWhen(ForestRequest forestRequest, ForestResponse forestResponse) {
        //没有异常、状态码等于200、数据中errcode等于0或者没有errcode,其他一律不认
        return forestResponse.noException()
                &&forestResponse.getStatusCode()==200
                &&(ObjectUtil.isEmpty(JSONUtil.parseObj(forestResponse.getContent()).getInt("errcode"))||0==JSONUtil.parseObj(forestResponse.getContent()).getInt("errcode"));
    }
}
