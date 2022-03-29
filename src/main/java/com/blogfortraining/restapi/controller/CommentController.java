/*
MIT License

Copyright (c) 2022 shadai-rafael

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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
import javax.validation.Valid;

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
                                                    @Valid @RequestBody CommentDTO commentDTO){
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
                                                    @Valid @RequestBody CommentDTO commentDTO){
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
