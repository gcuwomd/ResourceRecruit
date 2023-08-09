package com.example.resourceserver.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resourceserver.Service.Department;
import com.example.resourceserver.mapper.DepartmentMapper;
import com.example.resourceserver.mapper.UserMapper;
import com.example.resourceserver.pojo.Image;
import com.example.resourceserver.pojo.Users;
import com.example.resourceserver.pojo.Volunteer;
import com.example.resourceserver.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService implements Department {
    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public boolean nextDepartment(String id) {
        return   departmentMapper.nextDepartment(id);
    }

    @Override
    public ResultUtil choosePassPerson(String departmentId) {
        if (departmentExist(departmentId)){
            List list=departmentMapper.choosePassPerson(departmentId);
            JSONArray array = this.personDescript(list);
            return  ResultUtil.sucess(array);
        }

        return new ResultUtil(200,"部门编号错误",null);
    }

    @Override
    public ResultUtil chooseNoPassPerson(String departmentId) {
        if (departmentExist(departmentId)){
            List list=departmentMapper.chooseNotPassPerson(departmentId);
            JSONArray array = this.personDescript(list);
            return  ResultUtil.sucess(array);
        }

        return new ResultUtil(200,"部门编号错误",null);
    }

    @Override
    public ResultUtil chooseWillPassPerson(String departmentId) {
        if (departmentExist(departmentId)){
            List list=departmentMapper.chooseWillPassPerson(departmentId);
            JSONArray array = this.personDescript(list);
            return  ResultUtil.sucess(array);
        }

        return new ResultUtil(200,"部门编号错误",null);
    }

    @Override
    public boolean changeStatus(String id, Integer status) {
        if (status==1){
            departmentMapper.changeToPassStatus(id);
            return  departmentMapper.changeToPassStatus(id);
        }else if(status==-1){
         return     departmentMapper.changeToNoPassStatus(id
            );

        }
        return false ;
    }
    boolean departmentExist(String departmentId){
        return departmentMapper.departmentExist(departmentId);
    }
    JSONArray personDescript(List list){
        JSONArray array_userInfo=new JSONArray();
        for (Object item:list){
            JSONObject personSingle = (JSONObject) JSON.toJSON((Users)item);
            JSONObject volunteer=new JSONObject();
            JSONObject image=new JSONObject();
            List  list_image= userMapper.getImageById(((Users)item).getId());
            int i=1;
            for(Object images:list_image){
                image.put((Integer.toString(i++)),(((Image)images).getUrl()));
            }
            personSingle.put("image",image);
            array_userInfo.add(personSingle);

        };
        return array_userInfo;
    }


}
