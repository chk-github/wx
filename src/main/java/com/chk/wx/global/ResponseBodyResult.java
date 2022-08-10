package com.chk.wx.global;

import cn.hutool.core.util.ObjectUtil;
import com.chk.wx.entity.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseBodyResult
 * 全局返回处理
 * @author chk
 * @since 2022/4/19 10:08
 */
@RestControllerAdvice
public class ResponseBodyResult implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Unpack methodAnnotation = returnType.getMethodAnnotation(Unpack.class);
        return ObjectUtil.isEmpty(methodAnnotation);
    }

    @Override
    public Object beforeBodyWrite(Object body,  MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return Result.OK(body);
    }
}
