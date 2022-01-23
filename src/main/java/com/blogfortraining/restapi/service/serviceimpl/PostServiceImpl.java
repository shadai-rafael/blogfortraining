package com.blogfortraining.restapi.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.blogfortraining.restapi.entity.Post;
import com.blogfortraining.restapi.exception.ResourceNotFoundException;
import com.blogfortraining.restapi.payload.PostDTO;
import com.blogfortraining.restapi.payload.PostResponse;
import com.blogfortraining.restapi.repository.PostRepository;
import com.blogfortraining.restapi.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    private PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    private Post convertPostDTOToPost(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post copyPost = postRepository.save(post);
        return convertPostToPostDTO(copyPost);
    }

    @Override
    public PostResponse  getAllPost(int pageNo, int pageSize, String sortBy, String order) {

        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                    Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<Post> pagePost = postRepository.findAll(pageable);

        List<PostDTO> content = pagePost.getContent().stream().
                                map(post -> convertPostToPostDTO(post)).
                                collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLast(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostbyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", Long.toString(id)));
        return convertPostToPostDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", Long.toString(id)));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());     
        return convertPostToPostDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", Long.toString(id)));
        postRepository.delete(post);
        return;
    }
}
