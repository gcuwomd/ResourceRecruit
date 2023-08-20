package com.example.resource.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class Users {
    private  String id;
    private  String username;
    private String introduction;
    private  String major;
    private String college;
    private  String gender;
    private  String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Integer MessageStatus;

    public Users(String id, String username, String introduction, String major, String college, String gender,String phone) {
        this.id = id;
        this.username = username;
        this.introduction = introduction;
        this.major = major;
        this.college = college;
        this.gender = gender;
        this.phone=phone;
    }
}
