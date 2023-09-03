package com.example.resource.pojo;


import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class PageBean {
    private JSONArray data;

    private long total;
    public PageBean(JSONArray data,long total) {
        this.data = data;
        this.total = total;
    }


}
