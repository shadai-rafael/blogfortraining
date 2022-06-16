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
package com.blogfortraining.restapi.service.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import com.blogfortraining.restapi.entity.Comment;
import com.blogfortraining.restapi.entity.Post;
import com.blogfortraining.restapi.exception.InconsistentDataException;
import com.blogfortraining.restapi.exception.ResourceNotFoundException;
import com.blogfortraining.restapi.payload.CommentDTO;
import com.blogfortraining.restapi.repository.CommentRepository;
import com.blogfortraining.restapi.repository.PostRepository;
import com.blogfortraining.restapi.service.CommentService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                                PostRepository postRepository,
                                ModelMapper modelMapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private Comment convertCommentDTOToComment(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        return comment;       
    }

    private CommentDTO convertCommentToCommentDTO(Comment comment){
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        return commentDTO;        
    }
    
    @Override
    public CommentDTO creatCommentDTO(CommentDTO commentDTO, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->
            new ResourceNotFoundException("Post", "id", Long.toString(postId))
        );
        Comment comment = convertCommentDTOToComment(commentDTO);
        comment.setPost(post);
        return convertCommentToCommentDTO(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId) {
        List<Comment> lisfOfComments = commentRepository.findByPostId(postId);
        return lisfOfComments.stream().map(comment -> convertCommentToCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long commentId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
            new ResourceNotFoundException("Post", "id", Long.toString(postId))
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
            new ResourceNotFoundException("Comment", "id", Long.toString(commentId))
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new InconsistentDataException(HttpStatus.BAD_REQUEST, "There's not the post specified");
        }
        return convertCommentToCommentDTO(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->
            new ResourceNotFoundException("Post", "id", Long.toString(postId))
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
            new ResourceNotFoundException("Comment", "id", Long.toString(commentId))
        );
        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, Long postId, CommentDTO commentDTO){

        Post post = postRepository.findById(postId).orElseThrow(()->
            new ResourceNotFoundException("Post", "id", Long.toString(postId))
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
            new ResourceNotFoundException("Comment", "id", Long.toString(commentId))
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new InconsistentDataException(HttpStatus.BAD_REQUEST, "There's not the post specified");
        }
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return convertCommentToCommentDTO(commentRepository.save(comment));
    }
}
