package com.example.resource.pojo;

import lombok.Data;

@Data
public class UpdateStatus {
    private String id;
    private String department_id;
    private String status;
    private String MessageStatus;

    public UpdateStatus(String id, String department_id, String status, String MessageStatus) {
        this.id = id;
        this.department_id = department_id;
        this.status = status;
        this.MessageStatus = MessageStatus;
    }
}
