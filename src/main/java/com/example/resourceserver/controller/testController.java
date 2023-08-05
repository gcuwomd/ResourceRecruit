package com.example.resourceserver.controller;


import com.example.resourceserver.Service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author vains
 */
@RestController
public class testController {
@Autowired
User userService;

    @GetMapping("/test")
    public String test01() {
        return "test01";
    }

    @GetMapping("/test02")
    public String test02() {
        return "test02";
    }

    @GetMapping("/app")
    //权限字段写成和url一样，以此为例
    @PreAuthorize("hasAuthority('app')")
    public String app() {
        return "app";
    }


}

