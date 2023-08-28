package com.example.resource.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.pojo.Excel;
import com.example.resource.pojo.Users;
import com.example.resource.util.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface User {
    JSONArray allInfo(String  organizationId);
    JSONObject departmentData(String organizationId);

    JSONArray InfoByDepartment(String departmentId);

    @Transactional(rollbackFor = Exception.class)
    void register(@RequestBody JSONObject json);

    void postIp(HttpServletRequest request, String id);

    void updateUserInfo(Users updateUser, ArrayList updateArrayList);

    void deleteUser(String id);

    JSONArray InfoByPersonal(String organizationId, String key);

    boolean OssPut(MultipartFile image, String id);

   List sendMessage(JSONObject messageInfo);

    void deleteVolunteer(String id);

    void deleteImage(String id);

    void deleteStatus(String id);
}