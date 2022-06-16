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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    private Post convertPostDTOToPost(PostDTO postDTO){
        Post post = modelMapper.map(postDTO, Post.class);
        return post;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertPostDTOToPost(postDTO);
        return convertPostToPostDTO(postRepository.save(post));
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
