package com.example.resource.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.pojo.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface User {
    JSONArray allInfo(String  organizationId);
    JSONObject departmentData(String organizationId);

    JSONArray InfoByDepartment(String departmentId);
       void register(Users users, ArrayList arrayList);

    JSONArray InfoByPersonal(String organizationId, String key);

    boolean OssPut(MultipartFile image, String id);

   List sendMessage(JSONObject messageInfo);
}
