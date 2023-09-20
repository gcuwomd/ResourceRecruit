package com.example.resource.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
public class Comment {
    @JsonInclude(JsonInclude.Include.NON_NULL
    )
    private String id;
    private String comment;
}
