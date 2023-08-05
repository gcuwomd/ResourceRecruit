package com.example.resourceserver.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resourceserver.Service.User;
import com.example.resourceserver.mapper.UserMapper;
import com.example.resourceserver.pojo.Users;
import com.example.resourceserver.pojo.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService implements User {
    @Autowired
    UserMapper userMapper;

    @Override
    public String allInfo() {
        List <Users>list=userMapper.getAllInfo();
        JSONArray JSA=new JSONArray();
        for (Object item:list){
            JSONObject jsonObject = (JSONObject) JSON.toJSON((Users)item);
            JSONObject js=new JSONObject();
          List  list_volunteer= userMapper.getVolunteer(((Users)item).getId());
            for(Object user:list_volunteer){
                js.put(((Volunteer)user).getLevel(),((Volunteer)user).getDepartmentName());
            }
            jsonObject.put("volunteer",js);
            JSA.add(jsonObject);
        }
        String info=JSA.toJSONString();
        System.out.println(info);
        return info;
    }
}
