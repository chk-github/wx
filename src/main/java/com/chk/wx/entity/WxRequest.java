package com.chk.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 接口消息推送的实体类
 *
 * @author chk
 * @since 2022/4/18 9:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxRequest {
    private MultipartFile file;
    private String url;
    private String miniProgram;
    private String data;
    private List<String> toUsers;
    private String templateId;
}
