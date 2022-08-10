package com.chk.wx.service;


import com.chk.wx.entity.WxRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxService {
    /**
     * 验证消息的确来自微信服务器
     *
     * @param request   请求
     * @param response  响应
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @author chk
     * @date 2022/4/14 15:23
     **/
    void check(HttpServletRequest request, HttpServletResponse response, String signature, String timestamp, String nonce, String echostr);

    /**
     * 接收微信发送过来的各种消息
     *
     * @param request 请求
     * @author chk
     * @date 2022/04/14 16:03
     **/
    String attentionInfo(HttpServletRequest request) throws Exception;


    /**
     * 模板消息推送
     *
     * @author chk
     * @date 2022/4/14 17:00
     **/
    boolean push(WxRequest wxRequest);
}
