package com.chk.wx.entity;


/**
 * Code 常量
 */
public enum ResponseCode {

    /**
     * 参数说明
     */
    OK(10000, "请求成功"),
    ERROR(11111, "平台服务异常");

    /**
     * 值
     */
    public int  value;
    /**
     * 对应信息
     */
    public String msg;

    ResponseCode(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }
}
