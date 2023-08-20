package com.example.resource.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.resource.Service.User;
import com.example.resource.mapper.DepartmentMapper;
import com.example.resource.mapper.UserMapper;
import com.example.resource.pojo.Users;
import com.example.resource.util.OssUtil;
import com.example.resource.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    User user;
    @GetMapping ("/user/info/all")
    ResultUtil allinfo(@RequestParam String organizationId){
        if (!(userMapper.organizationExist(organizationId)))
        return  new ResultUtil(400,"组织编号不存在",null);
        return ResultUtil.sucess(user.allInfo(organizationId));
    }
    @PostMapping(value = "/putPhoto",consumes ="multipart/form-data")
    public synchronized ResultUtil descriptPhoto(@RequestParam("file") MultipartFile image, @RequestParam String id) throws IOException {
        String fileName=image.getOriginalFilename();
        System.out.println(fileName);
        if (!(fileName != null && (fileName.toLowerCase().endsWith(".jpg") ||
                fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".png") ||
                fileName.toLowerCase().endsWith(".gif") ||
                fileName.toLowerCase().endsWith(".bmp")))) {
            return  new ResultUtil(400,"只能上传图片",null);

        }
        if(id.length()!=12){
            return  new ResultUtil(403,"id不合法",null);
        }
        if(user.OssPut(image,id)){
            return  new ResultUtil(200,"上传成功",null);
        }
       return  new ResultUtil(400,"上传失败",null);
    }
    @GetMapping("/user/departmentData")
    public  ResultUtil  depatementData( @RequestParam String organizationId){
        if (!(userMapper.organizationExist(organizationId)))
            return  new ResultUtil(400,"组织编号不存在",null);
        return  ResultUtil.sucess(user.departmentData(organizationId));
    }
    @GetMapping("/user/info/department")
    public  ResultUtil infoByDepartmeent( @RequestParam String departmentId){
        if (!(departmentMapper.departmentExist(departmentId)))
            return  new ResultUtil(400,"部门不存在",null);
        return  ResultUtil.sucess(user.InfoByDepartment(departmentId));
    }    @GetMapping("/user/info/person")
    public  ResultUtil  InfoByPerson( @RequestParam String organizationId,String key){
        if (!(userMapper.organizationExist(organizationId)))
            return  new ResultUtil(400,"组织编号不存在",null);
        return  ResultUtil.sucess(user.InfoByPersonal(organizationId,key));
    }
    @PostMapping("user/register")
    ResultUtil register(@RequestBody JSONObject json){
        String id=json.getString("id");

        if (departmentMapper.userExist(id))  return new ResultUtil(403,"你已经报名",null);
        Users users=new Users
                (id,
                        json.getString("username"),
                        json.getString("introduction"),
                        json.getString("major"),
                        json.getString("college"),
                        json.getString("gender"),
                        json.getString("phone")
                        );
        ArrayList arrayList = (ArrayList) json.get("volunteer");
        user.register(users,arrayList);
        return ResultUtil.sucess();
    }
    @PostMapping("/send/message")
     ResultUtil sendMessage(@RequestBody JSONObject MessageInfo){
        List list = user.sendMessage(MessageInfo);
        if (list.isEmpty()) return  ResultUtil.sucess();
        return ResultUtil.sucess(list);

    }
    @PostMapping("/sms/callback")
    HashMap<String, Object> MessageReturn(@RequestBody JSONObject returnInfo){
        String mobile = returnInfo.getString("mobile");
        String text = returnInfo.getString("text");
        userMapper.insertMessage(mobile,text);
        HashMap<String ,Object> map= new HashMap<>();
        map.put("result",0);
        map.put("errmsg","OK");
        return map;
    }
    @GetMapping("/message/reply")
    ResultUtil getMessageReply(@RequestParam String departmentId){
        return ResultUtil.sucess( userMapper.geMessageByDepartment(departmentId));

    }

}

