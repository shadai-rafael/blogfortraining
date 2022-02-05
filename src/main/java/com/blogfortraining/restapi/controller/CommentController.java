package com.blogfortraining.restapi.controller;

import com.blogfortraining.restapi.payload.CommentDTO;
import com.blogfortraining.restapi.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController (CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/{postid}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postid")Long postId,
                                                    @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.creatCommentDTO(commentDTO, postId), HttpStatus.CREATED);
    }

    @GetMapping("/{postid}/comments")
    public List<CommentDTO> getallCommentsByPostId(@PathVariable(value = "postid") Long postId){
        return commentService.getCommentByPostId(postId);
    }

    @GetMapping("/{postid}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable (value = "postid") Long postId,
                                                     @PathVariable (value = "id") Long commentId){
        CommentDTO commentDTO = commentService.getCommentById(commentId, postId);
        return new ResponseEntity<>(commentDTO,HttpStatus.OK) ;  
    }

    @PutMapping("/{postid}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable (value = "postid") Long postId,
                                                    @PathVariable (value = "id") Long commentId,
                                                    @RequestBody CommentDTO commentDTO){
        CommentDTO updatedComment = commentService.updateComment(commentId, postId, commentDTO);                                                        
        return new ResponseEntity<> (updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{postid}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable (value = "postid") Long postId,
                                                    @PathVariable (value = "id") Long commentId){
        commentService.deleteComment(commentId, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
