package com.example.resourceserver.handler;

;
import com.example.resourceserver.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理
 * */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(Exception.class)
    public ResultUtil ex(Exception ex){
        log.info("全局处理");
        ex.printStackTrace();
        return ResultUtil.error();
    }
}

