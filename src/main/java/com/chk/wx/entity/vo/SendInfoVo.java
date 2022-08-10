package com.chk.wx.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送实体类(这个实体类字段的命名就这样,有意见保留)
 * @author chk
 * @version 1.0
 * @date 2021-10-13 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendInfoVo {

    /**
     * 接收方帐号（收到的OpenID）
     * @author chk
     **/
    private String ToUserName;

    /**
     * 开发者微信号 gh_053aa49a0b01
     * @author chk
     **/
    private String FromUserName;

    /**
     * 消息创建时间 （整型）
     * @author chk
     **/
    private Long CreateTime;

    /**
     * 消息类型，text、video、image
     * @author chk
     **/
    private String MsgType;

    /**
     * 回复内容
     **/
    private String Content;

    /**
     * 图片、语音、视频
     * 通过素材管理中的接口上传多媒体文件，得到的id。(图片消息时必传)
     * @author chk
     **/
    private String MediaId;

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


}
