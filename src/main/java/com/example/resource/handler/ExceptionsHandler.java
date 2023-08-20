package com.example.resource.handler;

import com.example.resource.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultUtil e(Exception ex){
        log.info("全局处理");
        ex.printStackTrace();
        return new ResultUtil(500,"请求方法错误",null);
    }
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public ResultUtil exs(Exception ex){
        log.info("全局处理");
        ex.printStackTrace();
        return new ResultUtil(500,"请求头错误",null);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultUtil exss(Exception ex){
        log.info("全局处理");
        ;
        return new ResultUtil(403,"缺少参数或参数错误",null);
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultUtil missParams(Exception ex){
        log.info("全局处理");
        ;
        return new ResultUtil(403,"缺少参数或参数错误",null);
    }
    @ExceptionHandler(Exception.class)
    public ResultUtil ex(Exception ex){
        log.info("全局处理");
        ex.printStackTrace();
        return new ResultUtil(500,"服务器内部异常",null);
    }

}

