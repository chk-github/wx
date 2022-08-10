package com.chk.wx.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * WxConfig
 *
 * @author chk
 * @since 2022/6/21 18:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    private String appId;
    private String appSecret;
    private String token;
    private String wxToken;
    private String oneMsgId;
    private List<String> userList;

    public boolean isToken(){
        return !ObjectUtils.isEmpty(token);
    }
}
