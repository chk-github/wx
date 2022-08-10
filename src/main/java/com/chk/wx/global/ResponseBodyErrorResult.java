package com.chk.wx.global;

import com.chk.wx.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ResponseBodyObject
 * 全局异常处理
 * @author chk
 * @since 2022/4/19 16:29
 */
@Slf4j
@RestControllerAdvice
public class ResponseBodyErrorResult {
    //缺少参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException ==> " + e.getMessage());
        return Result.Error(e.getParameterName() + "缺失");
    }

    //参数类型不匹配
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException ==> " + e.getMessage());
        return Result.Error("参数类型不匹配: " + e.getName() + "类型应该为" + e.getRequiredType());
    }

    //请求方式异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException ==> " +e.getMessage());
        return Result.Error("接口类型错误,应为" + Objects.requireNonNull(e.getSupportedMethods())[0]);
    }

    @ExceptionHandler(Exception.class)
    public Object Exception(Exception e) {
        e.printStackTrace();
        log.error("Exception ==> " + e.getMessage());
        return Result.Error(e.toString());
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Object nullException(NullPointerException e){
        log.error("NullPointerException ==> " + e.getMessage());
        return Result.Error(e.toString());
    }

    //绑定异常
    @ExceptionHandler(BindException.class)
    public Object BindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String collect = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        log.error("BindException ==> " + collect);
        return Result.Error(collect);
    }

    //json序列化异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object MismatchedInputException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException ==>" + e.getMessage());
        return Result.Error("body体参数异常" + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException e){
        log.error("IllegalArgumentException ==> " + e.getMessage());
        return Result.Error(e.getMessage());
    }

    /**
     * 方法参数无效的处理程序
     *
     * @param exception 异常
     * @return {@link Object }
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        //按需重新封装需要返回的错误信息
        List<String> message = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            String rejectedValue = error.getRejectedValue() + "";
            String defaultMessage = error.getDefaultMessage();
            String msg = field + "字段所填的{" + rejectedValue + "}不符合规则,请遵守{" + defaultMessage + "}";
            message.add(msg);
        }
        return Result.Error(message.get(0));
    }
}
