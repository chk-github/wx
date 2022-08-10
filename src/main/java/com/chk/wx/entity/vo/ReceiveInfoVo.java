package com.chk.wx.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收实体类(这个实体类字段的命名就这样,有意见保留)
 * @author chk
 * @version 1.0
 * @date 2021-10-13 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveInfoVo {

    /**
     * 开发者微信号 gh_053aa49a0b01
     * @author chk
     **/
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     * @author chk
     **/
    private String FromUserName;

    /**
     * 消息创建时间 （整型）
     * @author chk
     **/
    private Long CreateTime;

    /**
     * 消息类型，event
     * @author chk
     **/
    private String MsgType;

    /**
     * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     * @author chk
     **/
    private String Event;

    /**
     * 
     * 扫描带参数二维码事件
            * 用户未关注时，进行关注后的事件推送     事件KEY值，qrscene_为前缀，后面为二维码的参数值
            * 用户已关注时的事件推送                事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
     * 自定义菜单事件
            * 点击菜单拉取消息时的事件推送  事件KEY值，与自定义菜单接口中KEY值对应
            * 点击菜单跳转链接时的事件推送  事件KEY值，设置的跳转URL
     * @author chk
     **/
    private String EventKey;

    /**
     * 地理位置纬度
     * @author chk
     **/
    private Long Latitude;

    /**
     * 地理位置经度
     * @author chk
     **/
    private Long Longitude;

    /**
     * 地理位置精度
     * @author chk
     **/
    private Long Precision;

    /**
     * 二维码的ticket，可用来换取二维码图片
     * @author chk
     **/
    private String Ticket;

    /**
     * 携带的消息内容
     * @author chk
     **/
    private String Content;


}
