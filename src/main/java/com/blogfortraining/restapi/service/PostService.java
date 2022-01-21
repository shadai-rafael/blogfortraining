package com.blogfortraining.restapi.service;

import java.util.List;

import com.blogfortraining.restapi.payload.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPost();
    PostDTO getPostbyId(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePost(Long id);
}