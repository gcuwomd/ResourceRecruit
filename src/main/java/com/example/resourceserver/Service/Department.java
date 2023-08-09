package com.example.resourceserver.Service;

import com.example.resourceserver.util.ResultUtil;

public interface Department {
    boolean nextDepartment(String id);


    ResultUtil choosePassPerson(String departmentId);

    ResultUtil chooseNoPassPerson(String departmentId);

    ResultUtil chooseWillPassPerson(String departmentId);

    boolean changeStatus(String id, Integer status);
}
