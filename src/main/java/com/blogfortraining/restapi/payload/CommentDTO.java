package com.blogfortraining.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDTO {
//    private Long id;
    private String name;
    private String email;
    private String body;
}
