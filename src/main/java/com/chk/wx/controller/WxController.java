package com.chk.wx.controller;

import com.chk.wx.global.Unpack;
import com.chk.wx.service.WxService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * WxController
 *
 * @author chk
 * @since 2022/4/14 14:54
 */
@RestController
@RequestMapping("wxPublic")
public class WxController {

    private final WxService wxService;

    public WxController(WxService wxService) {
        this.wxService = wxService;
    }

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
     **/
    @GetMapping("/check")
    public void check(HttpServletRequest request,
                      HttpServletResponse response,
                      String signature,
                      String timestamp,
                      String nonce,
                      String echostr) throws IOException {
        wxService.check(request, response, signature, timestamp, nonce, echostr);
    }

    /**
     * 接收微信发送过来的各种消息
     *
     * @param request 请求
     * @author chk
     **/
    @Unpack
    @PostMapping("/check")
    public String attentionInfo(HttpServletRequest request) throws Exception {
        return wxService.attentionInfo(request);
    }

    /**
     * 创建自定义菜单
     * @author chk
     * @param map 自定义菜单
     **/
    @PostMapping("/createMenu")
    public void createMenu(@RequestBody HashMap<String,Object> map){
        wxService.createMenu(map);
    }
}
