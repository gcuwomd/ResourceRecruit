package com.example.resource.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultUtil {
    private Integer code;
    private String message;
    private Object data;

    public ResultUtil(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public  static ResultUtil sucess(Object data){
        return  new ResultUtil(200,"success",data);
    }
    public  static ResultUtil sucess(){
        return  new ResultUtil(200,"success",null);
    }
    public  static ResultUtil error(){
        return  new ResultUtil(0,"error",null);
    }

    @Override
    public String toString() {
        return "ResultUtil{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
