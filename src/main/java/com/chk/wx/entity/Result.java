package com.chk.wx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Response
 *
 * @author chk
 * @since 2022/4/19 10:12
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> OK(T data){
        return new Result<T>().setCode(ResponseCode.OK.value).setMessage(ResponseCode.OK.msg).setData(data);
    }
    public static <T> Result<T> Error(String msg){
        return new Result<T>().setCode(ResponseCode.ERROR.value).setMessage(msg.isEmpty()?ResponseCode.ERROR.msg:msg);
    }
}
