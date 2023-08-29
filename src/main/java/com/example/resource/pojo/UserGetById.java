package com.example.resource.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UserGetById {
    private String username;
    private String id;
    private String introduction;
    private String major;
    private String college;
    private String phone;
    private String gender;
    private List<Volunteer> volunteer;
    private List<Image> images;
}
