package com.example.resource.mapper;

import com.alibaba.fastjson.JSONArray;
import com.example.resource.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    List<Users> getAllInfo(String organizationId);

    List<Volunteer> getVolunteer(String id);

    List<Statistics> statistics(String organizationId);

    Integer totalNumber(String organizationId);

    boolean addImage(String id, String url);

    List<Image> getImageById(String id);

    boolean organizationExist(String organizationId);

    List<Users> getInfoByDepartment(String departmentId);

    List<Users> getInfoByKey(String organizationId, String key);

    boolean insertStatus(String id, String departmentId);

    boolean insertUsers(Users users);

    boolean insertVolunteerList(List list,String id);

    List<MessagePerson> getPersonById(String type, List list);

    boolean setMessageStatus(List list);

    boolean insertMessage(String phone, String text, String uuid);

    List<MessageDetail> geMessageByDepartment(String departmentId);



    void deleteUser(String id);

    void deleteVolunteer(String id);

    void deleteImage(String id);

    void deleteStatus(String id);

    void updateUser(Users updatedUser);



    Users getUserById(String id);

    void postIp(String ip, String id);

    String ipLookups(String ip);

    boolean ipCheck(String ip);

    Object getStatus(String id);

    void deleteMessages(String messageId);

    Object getMessages(String id);

    List<Image> getImage(String id);

    List<UpdateVolunteer> getVolunteers(String id);

    void updateStatus(UpdateStatus existStatus);

    UpdateStatus getStatusById(String updateStatus);

    Image getImageByid(String id);

    void updateImage(Image updateImage);

    Message getMessagesByUuid(String messageId);

    void updateMessage(Message updateMessage);

    void updateVolunteers(Volunteer volunteer);

    void updateVolunteer(ArrayList updatedVolunteerList);

    List<Excel> fetchUserData();

    UserGetById userGetById(String id);

    void registerImage(String id,String url);

    void registerUser(String id, String username, String introduction, String major, String college, String gender, String phone);


    void insertVolunteer(String id, ArrayList arrayList);


    void InsertStatus(String id, String departmentId);

    String invertDepartmentId(String departmentId);
}
