package com.blogfortraining.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDTO {
    private long id;
    private String title;
    private String description;
    private String content;
}
