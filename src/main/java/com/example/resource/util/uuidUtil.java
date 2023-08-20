package com.example.resource.util;

import java.util.UUID;

public class uuidUtil {
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        // 获取标准 UUID 的字符串形式
        String uuidString = uuid.toString();
        // 去除 UUID 中的所有横线
        String strippedUuid = uuidString.replaceAll("-", "");
        // 截取前16位字符
//        String sixteenDigitUuid = strippedUuid.substring(0, 16);
        return strippedUuid;
    }

}
