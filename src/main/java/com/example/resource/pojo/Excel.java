package com.example.resource.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Excel {
    @ExcelProperty("姓名")
    private String username;
    @ExcelProperty("学号")
    private String id;
    @ExcelProperty("介绍")
    private String introduction;
    @ExcelProperty("专业")
    private String major;
    @ExcelProperty("学院")
    private String college;
    @ExcelProperty("电话")
    private String phone;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("志愿")
    private String departmentNames;
    @ExcelProperty("地位")
    private String status;
    @ExcelProperty("图片链接")
    private String imageUrls;
}
