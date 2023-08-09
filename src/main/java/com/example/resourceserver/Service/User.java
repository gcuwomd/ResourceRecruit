package com.example.resourceserver.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resourceserver.pojo.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface User {
    JSONArray allInfo(String  organizationId);
    JSONObject departmentData(String organizationId);

    JSONArray InfoByDepartment(String departmentId);
       void register(Users users, ArrayList arrayList);

    JSONArray InfoByPersonal(String organizationId, String key);

    boolean OssPut(MultipartFile image, String id);
}
