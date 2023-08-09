package com.example.resourceserver.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resourceserver.Service.User;
import com.example.resourceserver.mapper.UserMapper;
import com.example.resourceserver.pojo.Image;
import com.example.resourceserver.pojo.Statistics;
import com.example.resourceserver.pojo.Users;
import com.example.resourceserver.pojo.Volunteer;
import com.example.resourceserver.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements User {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OssUtil ossUtil;
/**
 * 查询所有报名情况
 */
    @Override
    public JSONArray allInfo(String  organizationId) {
        //查看是否存在组织
        List <Users>list=userMapper.getAllInfo(organizationId);
      JSONArray info =this.personDescript(list);
        return info;
    }
    @Override
    public JSONObject departmentData(String organizationId){
        List list=userMapper.statistics(organizationId);
        JSONArray depatementData=new JSONArray();
        for (Object item:list){
            JSONObject jsonObject = (JSONObject) JSON.toJSON((Statistics)item);
            depatementData.add(jsonObject);
        }
        JSONObject total=new JSONObject();
        total.put("department",depatementData);
        total.put("total",userMapper.totalNumber(organizationId));
        return total;
    }
/**
 * @param departmentId  根据部门id选取出部门的对应部门的报名人员信息
 *
 *
 * */
    @Override
    public JSONArray InfoByDepartment(String departmentId) {
        List<Users> infoByDepartment = userMapper.getInfoByDepartment(departmentId);
        JSONArray info=this.personDescript(infoByDepartment);
        return info;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void register(Users users, ArrayList arrayList) {
        userMapper.insertUsers(users);
        String id=users.getId();
        for (Object item:arrayList){
            JSONObject object= (JSONObject) JSON.toJSON(item);
            Integer level=Integer.parseInt(object.getString("level"));
            userMapper.insertVolunteerList(id,object.getString("volunteer"),level);
            if(level==1){
                userMapper.insertStatus(id,object.getString("volunteer"));
            }
        }
    }

    /**
     * @param key  根据关键词查找用户信息
     *
    * */

    @Override
    public JSONArray InfoByPersonal(String organizationId,String key) {
        List<Users> infoByDepartment = userMapper.getInfoByKey(organizationId,key);
        JSONArray info=this.personDescript(infoByDepartment);
        return info;
    }

    @Override
    public boolean OssPut(MultipartFile image, String id)  {
        URL url=ossUtil.put(image,id);
        String urls=url.toString();
        if(userMapper.addImage(id,urls))return  true;
        return false;
    }

    JSONArray personDescript(List list){
        JSONArray array_userInfo=new JSONArray();
        for (Object item:list){
            JSONObject personSingle = (JSONObject) JSON.toJSON((Users)item);
            JSONObject volunteer=new JSONObject();
            JSONObject image=new JSONObject();
            List  list_volunteer= userMapper.getVolunteer(((Users)item).getId());
            for(Object user:list_volunteer){
                volunteer.put(((Volunteer)user).getLevel(),((Volunteer)user).getDepartmentName());
            }
            List  list_image= userMapper.getImageById(((Users)item).getId());
            int i=1;
            for(Object images:list_image){
                image.put((Integer.toString(i++)),(((Image)images).getUrl()));
            }
            personSingle.put("volunteer",volunteer);
            personSingle.put("image",image);
            array_userInfo.add(personSingle);

        };
        return array_userInfo;
    }

}
