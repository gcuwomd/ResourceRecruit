package com.example.resource.mapper;

import com.example.resource.pojo.*;
import org.apache.ibatis.annotations.Mapper;

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

    boolean insertVolunteerList(String id, String departmentId, Integer level);

    List<MessagePerson> getPersonById(String type, List list);

    boolean setMessageStatus(List list);

    boolean insertMessage(String phone, String text);

    List<MessageDetail> geMessageByDepartment(String departmentId);
}
