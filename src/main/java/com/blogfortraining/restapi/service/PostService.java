package com.blogfortraining.restapi.service;

import com.blogfortraining.restapi.payload.PostDTO;
import com.blogfortraining.restapi.payload.PostResponse;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String order);
    PostDTO getPostbyId(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePost(Long id);
}