package com.example.resource.pojo;

import lombok.Data;

@Data
class GetInfo {
private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    private Image image;
private Volunteer volunteer;

        // 构造函数和 getter/setter 方法
        }

