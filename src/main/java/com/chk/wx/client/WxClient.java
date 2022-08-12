package com.chk.wx.client;

import cn.hutool.json.JSONObject;
import com.chk.wx.interceptor.ForestInterceptor;
import com.chk.wx.interceptor.SuccessCondition;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.callback.OnSuccess;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 微信请求客户端
 *
 * @author chk
 **/
@Component
@BaseRequest(
        baseURL = "${url}"
        , interceptor = ForestInterceptor.class//配置拦截器(可以配置多个)
)
@Success(condition = SuccessCondition.class)
public interface WxClient {


    /**
     * 发送模板消息
     * @param token     token
     * @param map       请求数据
     * @param onSuccess 成功回调
     * @author chk
     **/
    @Post(url = "/cgi-bin/message/template/send?access_token={token}")
    void send(@Var("token") String token, @JSONBody LinkedHashMap<String, Object> map, OnSuccess<JSONObject> onSuccess);

    /**
     * 获取Access token
     * @param appId     appId
     * @param appSecret appSecret
     * @author chk
     **/
    @Get("/cgi-bin/token?grant_type=client_credential")
    //请求例子:https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxcf167fb0b054ae27&secret=bb838eb790a84e03094c1c12c4edaef7
    void getToken(@Query("appid") String appId, @Query("secret") String appSecret, OnSuccess<JSONObject> onSuccess);

    /**
     * 获取用户列表(关注人数小于10000)
     * @author chk
     **/
    @Get("/cgi-bin/user/get?access_token={token}")
    void getUserList(@Var("token") String token, OnSuccess<JSONObject> onSuccess);


    /**
     * 创建自定义菜单
     * @author chk
     **/
    @Post("/cgi-bin/menu/create?access_token={token}")
    void createMenu(@Var("token")String token, @JSONBody HashMap<String, Object> map, OnSuccess<JSONObject> onSuccess);
}
