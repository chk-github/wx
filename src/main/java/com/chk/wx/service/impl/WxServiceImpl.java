package com.chk.wx.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chk.wx.client.WxClient;
import com.chk.wx.config.WxConfig;
import com.chk.wx.entity.WxRequest;
import com.chk.wx.entity.vo.ReceiveInfoVo;
import com.chk.wx.entity.vo.SendInfoVo;
import com.chk.wx.service.WxService;
import com.chk.wx.utils.SignUtil;
import com.chk.wx.utils.TimeUtil;
import com.dtflys.forest.Forest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * WxServiceImpl
 *
 * @author chk
 * @since 2022/4/14 15:25
 */
@Slf4j
@Service
public class WxServiceImpl implements WxService {
    /**
     * 消息类型:事件
     */
    public static final String MESSAGE_EVENT = "event";
    /**
     * 消息类型:文本
     **/
    public static final String MESSAGE_TEXT = "text";

    /**
     * 消息类型:图片
     **/
    public static final String MESSAGE_IMAGE = "image";

    /**
     * 消息类型:视频
     **/
    public static final String MESSAGE_VIDEO = "video";
    /**
     * 关注的事件类型
     */
    private static final String SUBSCRIBE = "subscribe";

    /**
     * 自定义菜单事件
     **/
    private static final String CLICK="CLICK";

    private final WxConfig wxConfig;
    private final WxClient wxClient;

    public WxServiceImpl(WxConfig wxConfig, WxClient wxClient) {
        this.wxConfig = wxConfig;
        this.wxClient = wxClient;
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
    @Override
    public void check(HttpServletRequest request, HttpServletResponse response, String signature, String timestamp, String nonce, String echostr) throws IOException {
        log.info("验证消息是否来自微信服务器");
        if (SignUtil.checkSignature(wxConfig.getWxToken(), signature, timestamp, nonce)) {
            //原样返回echostr参数内容,表示接入成功
            PrintWriter out = response.getWriter();
            out.print(echostr);
            out.close();
        } else {
            log.info("这里存在非法请求！");
        }
    }

    /**
     * 创建自定义菜单
     *
     * @author chk
     **/
    @Override
    public void createMenu(HashMap<String,Object> map) {
        //发送请求
        wxClient.createMenu(
                wxConfig.getToken(),map,
                (data, request, response) -> log.info("创建自定义菜单成功{}",data));
    }

    /**
     * 接收微信发送过来的各种消息
     *
     * @param request 请求
     * @author chk
     **/
    @Override
    public String attentionInfo(HttpServletRequest request) throws Exception {
        log.info("接收到来自微信的事件请求");
        // 获取HTTP请求的输入流
        // 已HTTP请求输入流建立一个BufferedReader对象
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String buffer;
        // 存放请求内容
        StringBuilder xml = new StringBuilder();
        while ((buffer = br.readLine()) != null) {
            // 在页面中显示读取到的请求参数
            xml.append(buffer);
        }
        //关闭流
        br.close();
        String callbackMessage = xml.toString();
        if (ObjectUtil.isNotNull(callbackMessage)) {
            //转换微信推送的xml数据包为实体类
            JSONObject parseFromXml = JSONUtil.parseFromXml(callbackMessage);
            ReceiveInfoVo attentionInfoVo = parseFromXml.getBean("xml", ReceiveInfoVo.class);
            //获取到事件的相关类型  进行对应操作(返回给微信)
            if (ObjectUtil.isNotNull(attentionInfoVo)) {
                SendInfoVo send = new SendInfoVo();
                send.setFromUserName(String.format("<![CDATA[%s]]>", attentionInfoVo.getToUserName()));
                send.setToUserName(String.format("<![CDATA[%s]]>", attentionInfoVo.getFromUserName()));
                send.setCreateTime(System.currentTimeMillis() / 1000);
                send.setMsgType("<![CDATA[text]]>");
                switch (attentionInfoVo.getMsgType()) {
                    case MESSAGE_EVENT://事件推送
                        //关注事件
                        if (SUBSCRIBE.equals(attentionInfoVo.getEvent())) {
                            send.setContent("<![CDATA[欢迎关注颜总de专属小助手]]>");
                        }
                        //自定义菜单事件
                        if(CLICK.equals(attentionInfoVo.getEvent())){
                            String eventKey = attentionInfoVo.getEventKey();
                            if("MARK".equals(eventKey)){
                                String msg = "<![CDATA[" + "今天是我们在一起的第" + TimeUtil.differentDays(new SimpleDateFormat("yyyy-MM-dd").parse("2022-06-04"), new Date()) + "天" + "\n" +
                                        "今天距离你的生日还剩" + TimeUtil.differentDays(new Date(), new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-19")) + "天" +
                                        "]]>";
                                send.setContent(msg);
                            }
                        }
                        break;
                    case MESSAGE_TEXT://文本消息
                        System.out.println(attentionInfoVo.getToUserName());
                        send.setContent(String.format("<![CDATA[%s]]>", attentionInfoVo.getContent()));
                        break;
                    case MESSAGE_IMAGE://图片消息
                        System.out.println("图片消息");
                        break;
                    case MESSAGE_VIDEO://视频消息
                        System.out.println("视频消息");
                        break;
                    default:
                        break;
                }
                return HtmlUtils.htmlUnescape("<xml>" + JSONUtil.toXmlStr(JSONUtil.parse(send)) + "</xml>");
            }
        }
        return null;
    }


    /**
     * 模板消息推送
     *
     * @author chk
     **/
    @Override
    public boolean push(WxRequest wxRequest) {
        boolean flag = false;
        log.info("推送模板消息");
        //1.获取公众号微信列表
        //2.将文件上传到微信素材库(先这样干后面有需要再改动)
        if (ObjectUtil.isNotEmpty(wxRequest.getFile())) {
            try {
                JSONObject execute = Forest.post("https://api.weixin.qq.com/cgi-bin/media/uploadimg")
                        .addQuery("access_token", wxConfig.getToken())
                        .contentTypeMultipartFormData()
                        .addFile("media", wxRequest.getFile().getInputStream(), wxRequest.getFile().getOriginalFilename(), wxRequest.getFile().getContentType())
                        .execute(JSONObject.class);
                if (ObjectUtil.isNotEmpty(execute.get("url"))) {
                    wxRequest.setUrl(execute.get("url", String.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //3.封装消息体
        if (StrUtil.isNotBlank(wxRequest.getData())) {
            flag = packageMsgBody(wxRequest, wxRequest.getToUsers());
        }
        return flag;
    }

    /**
     * @param wxRequest  微信公众号数据
     * @param userOpenId 关注用户的openId
     * @return boolean
     * @author chk
     **/
    private boolean packageMsgBody(WxRequest wxRequest, List<String> userOpenId) {
        if (!ObjectUtils.isEmpty(userOpenId) && !ObjectUtils.isEmpty(wxRequest)) {
            userOpenId.forEach(openId -> {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                //2.1接收者openid
                map.put("touser", openId);
                //2.2模板id
                map.put("template_id", wxRequest.getTemplateId());
                //2.3跳转url
                if (!ObjectUtils.isEmpty(wxRequest.getUrl())) {
                    map.put("url", wxRequest.getUrl());
                }
                //2.4跳转小程序数据
                //2.5模板数据
                map.put("data", JSONUtil.toBean(wxRequest.getData(), LinkedHashMap.class));
                //3.发送模板消息
                sendMsg(map);
            });
            return true;
        }
        log.error("无法发送微信消息推送");
        return false;
    }

    /**
     * 发送消息
     *
     * @param map 消息体
     * @author chk
     **/
    private void sendMsg(LinkedHashMap<String, Object> map) {
        if (wxConfig.isToken()) {
            wxClient.send(wxConfig.getToken(), map,
                    (data, request, response) -> log.info("模板消息推送成功:{}", data)
            );
        }
    }

    /**
     * 获取用户openId
     *
     * @author chk
     **/

    @SuppressWarnings("unused")
    private List<String> getUserOpenId() {
        AtomicReference<List<String>> openIds = new AtomicReference<>();
        if (wxConfig.isToken()) {
            wxClient.getUserList(
                    wxConfig.getToken(),
                    (data, request, response) -> {
                        JSONObject userData = data.getJSONObject("data");
                        if (!ObjectUtils.isEmpty(userData)) {
                            openIds.set(userData.getJSONArray("openid")
                                    .toBean(new TypeReference<List<String>>() {
                                    }));
                        }
                    }
            );
        }
        return openIds.get();
    }
}
