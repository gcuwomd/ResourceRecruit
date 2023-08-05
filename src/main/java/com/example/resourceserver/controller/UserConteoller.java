package com.example.resourceserver.controller;

import com.example.resourceserver.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserConteoller {
    @GetMapping ("/user/info/all")
    ResultUtil allinfo(){

        return ResultUtil.sucess();
    }
}

