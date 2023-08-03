package com.example.resourceserver.util;

import java.util.Random;
/**
* 随机生成返回四位随机数字
* */
public class CodeUtil {
     public  static  String createCode(){
         Random random = new Random();
         int code = random.nextInt(9000) + 1000;
         return String.valueOf(code);
    }
}
