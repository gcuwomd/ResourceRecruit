package com.example.resource.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.Service.User;
import com.example.resource.Service.impl.UserService;
import com.example.resource.mapper.DepartmentMapper;
import com.example.resource.mapper.UserMapper;
import com.example.resource.pojo.*;
import com.example.resource.util.OssUtil;
import com.example.resource.util.ResultUtil;
import com.example.resource.util.uuidUtil;
import com.qcloud.cos.utils.IOUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    User user;

    @GetMapping("/user/info/all")
    ResultUtil allinfo(@RequestParam String organizationId) {
        if (!(userMapper.organizationExist(organizationId)))
            return new ResultUtil(400, "组织编号不存在", null);
        return ResultUtil.sucess(user.allInfo(organizationId));
    }

    @PostMapping(value = "/putPhoto", consumes = "multipart/form-data")
    public synchronized ResultUtil descriptPhoto(@RequestParam("file") MultipartFile image, @RequestParam String id) throws IOException {
        String fileName = image.getOriginalFilename();
        System.out.println(fileName);
        if (!(fileName != null && (fileName.toLowerCase().endsWith(".jpg") ||
                fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".png") ||
                fileName.toLowerCase().endsWith(".gif") ||
                fileName.toLowerCase().endsWith(".bmp")))) {
            return new ResultUtil(400, "只能上传图片", null);
        }
        if (id.length() != 12) {
            return new ResultUtil(403, "id不合法", null);
        }
        if (user.OssPut(image, id)) {
            return new ResultUtil(200, "上传成功", null);
        }
        return new ResultUtil(400, "上传失败", null);
    }

    @GetMapping("/user/departmentData")
    public ResultUtil depatementData(@RequestParam String organizationId) {
        if (!(userMapper.organizationExist(organizationId)))
            return new ResultUtil(400, "组织编号不存在", null);
        return ResultUtil.sucess(user.departmentData(organizationId));
    }

    @GetMapping("/user/info/department")
    public ResultUtil infoByDepartmeent(@RequestParam String departmentId) {
        if (!(departmentMapper.departmentExist(departmentId)))
            return new ResultUtil(400, "部门不存在", null);
        return ResultUtil.sucess(user.InfoByDepartment(departmentId));
    }

    @GetMapping("/user/info/person")
    public ResultUtil InfoByPerson(@RequestParam String organizationId, String key) {
        if (!(userMapper.organizationExist(organizationId)))
            return new ResultUtil(400, "组织编号不存在", null);
        return ResultUtil.sucess(user.InfoByPersonal(organizationId, key));
    }

    @PostMapping("user/register")
    ResultUtil register(HttpServletRequest request,@RequestBody JSONObject json) {
        String ip = request.getHeader("X-Real-IP");
        String id = json.getString("id");
        userMapper.postIp(ip,id);
        if (user.register(json)) {
            return ResultUtil.sucess();
        }
       return  ResultUtil.error();
    }


    @PutMapping("/user/updateImage")
    public ResultUtil update(HttpServletRequest request,@RequestBody JSONObject json){
        String id = json.getString("id");

        if (!departmentMapper.userExist(id)) {
            return new ResultUtil(403, "你还未报名，请检查学号", null);
        }
        Image updateImage = userMapper.getImageByid(id);
        if (updateImage != null) {
            if (json.containsKey("url")) {
                updateImage.setUrl(json.getString("url"));
            }
            userMapper.updateImage(updateImage);
            return new ResultUtil(200, "用户信息已更新", updateImage);
        } else {
            return new ResultUtil(404, "用户不存在", null);
        }
    }

    @PutMapping("/user/updateUser")
    public ResultUtil updateUser(HttpServletRequest request,@RequestBody JSONObject json) {
//        String ip = request.getHeader("X-Real-IP");
//        String ip = request.getHeader("X-Forwarded-For");
//        String ip = request.getHeader("X-Forwarded-Proto");
//          String ip = request.getHeader("REMOTE-HOST");

        String id = json.getString("id");
//        userMapper.postIp(ip,id);
        if (!departmentMapper.userExist(id)) {
            return new ResultUtil(403, "你还未报名，请检查学号", null);
        }
        Users existingUser = userMapper.getUserById(id);
        Image updateImage = userMapper.getImageByid(id);
        if (existingUser != null) {
            if (json.containsKey("username")) {
                existingUser.setUsername(json.getString("username"));
            }
            if (json.containsKey("introduction")) {
                existingUser.setIntroduction(json.getString("introduction"));
            }
            if (json.containsKey("major")) {
                existingUser.setMajor(json.getString("major"));
            }
            if (json.containsKey("college")) {
                existingUser.setCollege(json.getString("college"));
            }
            if (json.containsKey("phone")) {
                existingUser.setPhone(json.getString("phone"));
            }
            if (json.containsKey("gender")) {
                existingUser.setGender(json.getString("gender"));
            }
            userMapper.updateUser(existingUser);
            return new ResultUtil(200, "用户信息已更新", existingUser);
        } else {
            return new ResultUtil(404, "用户不存在", null);
        }

    }



    @GetMapping("/user/ip")
    ResultUtil getUserByIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String id = userMapper.ipLookups(ip);
        if (id != null) {
            return new ResultUtil(200, "查询成功",  user.getUserByIp(id));
        } else {
            return new ResultUtil(404, "用户不存在", null);
        }
    }

    @DeleteMapping("/user/delete")
    ResultUtil deleteUser(@RequestParam String id) {
        if (!departmentMapper.userExist(id)) return new ResultUtil(400, "没有此用户", null);
        user.deleteUser(id);
        user.deleteVolunteer(id);
        user.deleteImage(id);
        user.deleteStatus(id);
        return ResultUtil.sucess();
    }

    @PostMapping("/send/message")
    ResultUtil sendMessage(@RequestBody JSONObject MessageInfo) {
        List list = user.sendMessage(MessageInfo);
        if (list.isEmpty()) return ResultUtil.sucess();
        return ResultUtil.sucess(list);

    }

    @PostMapping("/sms/callback")
    HashMap<String, Object> MessageReturn(@RequestBody JSONObject returnInfo) {
        String mobile = returnInfo.getString("mobile");
        String text = returnInfo.getString("text");
        String uuid = uuidUtil.generateUUID();
        userMapper.insertMessage(mobile, text, uuid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", 0);
        map.put("errmsg", "OK");
        return map;
    }

    @DeleteMapping("/message/delete")
    ResultUtil messageDelete(@RequestParam String messageId) {
        userMapper.deleteMessages(messageId);
        return ResultUtil.sucess();
    }


    @GetMapping("/message/reply")
    ResultUtil getMessageReply(@RequestParam String departmentId) {
        return ResultUtil.sucess(userMapper.geMessageByDepartment(departmentId));

    }

    @GetMapping("download/excel")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Excel.class).sheet("模板").doWrite(getExcelData());
    }
    private List<Excel> getExcelData() {
        List<Excel> userData = userMapper.fetchUserData();
        return userData;
    }

}


