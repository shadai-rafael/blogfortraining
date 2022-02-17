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
