package com.example.resource.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UserById {
    private String username;
    private String id;
    private String introduction;
    private String major;
    private String college;
    private String phone;
    private String gender;
}