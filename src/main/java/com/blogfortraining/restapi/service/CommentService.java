package com.blogfortraining.restapi.service;

import com.blogfortraining.restapi.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    public CommentDTO creatCommentDTO(CommentDTO comment, Long postId);
    public List<CommentDTO> getCommentByPostId(Long postId);
    public CommentDTO getCommentById(Long commentId, Long postId);
    public CommentDTO updateComment(Long commentId, Long postId, CommentDTO commentDTO);
    public void deleteComment(Long commentId, Long postId);
}
