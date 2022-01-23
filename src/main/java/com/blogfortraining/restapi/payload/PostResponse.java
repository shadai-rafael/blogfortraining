package com.blogfortraining.restapi.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {    
    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
