package com.example.resource.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.pojo.Comment;
import com.example.resource.pojo.PageBean;
import com.example.resource.pojo.Users;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface User {
    PageBean allInfo(String  organizationId, Integer page, Integer pageSize);
    JSONObject departmentData(String organizationId);

    JSONArray InfoByDepartment(String departmentId);

    @Transactional(rollbackFor = Exception.class)
    boolean register(@RequestBody JSONObject json);


    JSONObject getUserByIp(String id);

    void updateUserInfo(Users updateUser, ArrayList updateArrayList);

    void deleteUser(String id);

    JSONArray InfoByPersonal(String organizationId, String key);

    boolean OssPut(MultipartFile image, String id);

   List sendMessage(JSONObject messageInfo);

    void deleteVolunteer(String id);

    void deleteImage(String id);

    void deleteStatus(String id);

    boolean rememberInComment(Comment comment);

    List <Comment> getComment(String id);
}
