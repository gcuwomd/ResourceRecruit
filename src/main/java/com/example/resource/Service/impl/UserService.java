package com.example.resource.Service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.Listener.ExcelDataListener;
import com.example.resource.Service.User;
import com.example.resource.mapper.ListenerMapper;
import com.example.resource.mapper.UserMapper;
import com.example.resource.pojo.*;
import com.example.resource.util.MeaasgeUtil;
import com.example.resource.util.OssUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements User {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    MeaasgeUtil meaasgeUtil;


    /**
     * 查询所有报名情况
     */
    @Override
    public PageBean allInfo(String organizationId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        //查看是否存在组织
        List<Users> list = userMapper.getAllInfo(organizationId);
        Page<Users> p = (Page<Users>) list;
        JSONArray info = this.personDescript(list);
        PageBean pageBean = new PageBean(info,p.getTotal());
        return pageBean;
    }

    @Override
    public JSONObject departmentData(String organizationId) {
        List list = userMapper.statistics(organizationId);
        JSONArray depatementData = new JSONArray();
        for (Object item : list) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON((Statistics) item);
            depatementData.add(jsonObject);
        }
        JSONObject total = new JSONObject();
        total.put("department", depatementData);
        total.put("total", userMapper.totalNumber(organizationId));
        return total;
    }

    /**
     * @param departmentId 根据部门id选取出部门的对应部门的报名人员信息
     */
    @Override
    public JSONArray InfoByDepartment(String departmentId) {
        List<Users> infoByDepartment = userMapper.getInfoByDepartment(departmentId);
        JSONArray info = this.personDescript(infoByDepartment);
        return info;
    }

    @Override
    public boolean register(JSONObject json) {
        String id = json.getString("id");
        String username = json.getString("username");
        String introduction = json.getString("introduction");
        String major = json.getString("major");
        String college = json.getString("college");
        String gender = json.getString("gender");
        String phone = json.getString("phone");
        JSONArray volunteerArray = json.getJSONArray("volunteer");

        userMapper.registerUser(id, username, introduction, major, college, gender, phone);
        userMapper.deleteVolunteer(id);
        userMapper.insertVolunteerList(volunteerArray,id);
        for (Object item : volunteerArray) {
            JSONObject object = (JSONObject) JSON.toJSON(item);
            Integer level = Integer.parseInt(object.getString("level"));
            if (level == 1) {
                userMapper.InsertStatus(id,object.getString("departmentId"));
            }
        }
        return true;
    }

    @Override
    public JSONObject getUserByIp(String id){
        UserGetById userGetById = userMapper.userGetById(id);

        List<UpdateVolunteer> volunteers = userMapper.getVolunteers(id);

        JSONObject json = (JSONObject) JSON.toJSON(userGetById);
        json.put("volunteer", volunteers);
        return json;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Users updatedUser, ArrayList updatedVolunteerList) {
        userMapper.updateUser(updatedUser);
        String id = updatedUser.getId();
        for (Object item : updatedVolunteerList) {
            JSONObject object = (JSONObject) JSON.toJSON(item);
            Integer level = Integer.parseInt(object.getString("level"));
            userMapper.updateVolunteer(updatedVolunteerList);
            if (level == 1) {
                userMapper.insertStatus(id, object.getString("volunteer"));
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String id){
        userMapper.deleteUser(id);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVolunteer(String id){
        userMapper.deleteVolunteer(id);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteImage(String id){
        userMapper.deleteImage(id);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStatus(String id){
        userMapper.deleteStatus(id);
    }



    /**
     * @param key 根据关键词查找用户信息
     */

    @Override
    public JSONArray InfoByPersonal(String organizationId, String key) {
        List<Users> infoByDepartment = userMapper.getInfoByKey(organizationId, key);
        JSONArray info = this.personDescript(infoByDepartment);
        return info;
    }

    @Override
    public boolean OssPut(MultipartFile image, String id) {
        URL url = ossUtil.put(image);
        String urls = url.toString();
        if (userMapper.addImage(id, urls)) return true;
        return false;
    }

    @Override
    public List sendMessage(JSONObject messageInfo) {
        List sucessList = new ArrayList<>();
        List failList = new ArrayList<>();
        String type = messageInfo.getString("type");
        List list = (List) messageInfo.get("users");
        List<MessagePerson> personList = userMapper.getPersonById(type, list);
        if (type.equals("pass")) {
            for (MessagePerson messagePerson : personList) {
                if ((meaasgeUtil.sendPassMessage(messagePerson)).equals("Ok")) {
                    sucessList.add(messagePerson.getId());
                } else {
                    failList.add(messagePerson.getId());
                }

            }
            if (!(sucessList.isEmpty())) userMapper.setMessageStatus(list);

            return failList;
        } else if (type.equals("face")) {
            for (MessagePerson messagePerson : personList) {
                meaasgeUtil.sendFaceMessage(messagePerson);
                if ((meaasgeUtil.sendPassMessage(messagePerson)).equals("Ok")) {
                    sucessList.add(messagePerson.getId());
                }
                if (!(sucessList.isEmpty())) failList.add(messagePerson.getId());
            }
        }else if (type.equals("fail")){
            for (MessagePerson messagePerson : personList) {
                meaasgeUtil.sendFaceMessage(messagePerson);
                if ((meaasgeUtil.sendPassMessage(messagePerson)).equals("Ok")) {
                    sucessList.add(messagePerson.getId());
                }
                if (!(sucessList.isEmpty())) failList.add(messagePerson.getId());
            }
        }else {

        }
        return failList;
    }

    JSONArray personDescript(List list) {
        JSONArray array_userInfo = new JSONArray();
        for (Object item : list) {
            JSONObject personSingle = (JSONObject) JSON.toJSON((Users) item);
            JSONObject volunteer = new JSONObject();
            JSONObject image = new JSONObject();
            List list_volunteer = userMapper.getVolunteer(((Users) item).getId());
            for (Object user : list_volunteer) {
                volunteer.put(((Volunteer) user).getLevel(), ((Volunteer) user).getDepartmentName());
            }
            List list_image = userMapper.getImageById(((Users) item).getId());
            int i = 1;
            for (Object images : list_image) {
                image.put((Integer.toString(i++)), (((Image) images).getUrl()));
            }
            personSingle.put("volunteer", volunteer);
            personSingle.put("image", image);
            array_userInfo.add(personSingle);

        }
        return array_userInfo;
    }
}
