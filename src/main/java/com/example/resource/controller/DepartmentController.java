package com.example.resource.controller;

import com.example.resource.Service.Department;
import com.example.resource.mapper.DepartmentMapper;
import com.example.resource.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    Department department;
@GetMapping("/user/pass")
    ResultUtil pass(@RequestParam String departmentId){
    if (!( departmentMapper.departmentExist(departmentId)))
        return  new ResultUtil(400,"无此部门",null);
    return  department.choosePassPerson(departmentId);
}
    @GetMapping("/user/noPass")
    ResultUtil Nopass(@RequestParam String departmentId){
        if (!( departmentMapper.departmentExist(departmentId)))
            return  new ResultUtil(400,"无此部门",null);
        return  department.chooseNoPassPerson(departmentId);
    }
    @GetMapping("/user/willPass")
    ResultUtil willPass (@RequestParam String departmentId){
        if (!( departmentMapper.departmentExist(departmentId)))
            return  new ResultUtil(400,"无此部门",null);
        return  department.chooseWillPassPerson(departmentId);
    }
    @PostMapping("user/nextDepartment")
    ResultUtil nextDepartment(@RequestBody Map<String ,Object > map){
    String id= (String) map.get("id");
    if(!(departmentMapper.userExist(id))) return  new ResultUtil(400,"没有此id信息",null);
    if(department.nextDepartment(id))
        return ResultUtil.sucess();
    return ResultUtil.error();
    }
    @PostMapping("/user/status")
    ResultUtil changeStatus(@RequestBody Map<String ,Object > map){
        String id= (String) map.get("id");
        if(!(departmentMapper.userExist(id))) return  new ResultUtil(400,"没有此id信息",null);
        String status= (String) map.get("status");
        System.out.println(status);
       //判断是否为数字
        if (! (status.matches("-?\\d+(\\.\\d+)?"))) return  new ResultUtil(400,"status参数错误",null);
        Integer statu =Integer.parseInt(status);
        if ((department.changeStatus(id,statu))) return  ResultUtil.sucess();
        return  new ResultUtil(403,"status取值错误",null);
    }

}
