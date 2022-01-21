package com.blogfortraining.restapi.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.blogfortraining.restapi.entity.Post;
import com.blogfortraining.restapi.exception.ResourceNotFoundException;
import com.blogfortraining.restapi.payload.PostDTO;
import com.blogfortraining.restapi.repository.PostRepository;
import com.blogfortraining.restapi.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PostDTO> getAllPost() {
        Iterable<Post> iterList = postRepository.findAll();
        List<Post> postList = new ArrayList<Post>();
        iterList.iterator().forEachRemaining(postList::add);
 /*       
        List<PostDTO> postListDTO = new LinkedList<PostDTO>();
        postList.forEach(post -> postListDTO.add(convertPostToPostDTO(post)));
*/
        return postList.stream().
                    map(post -> convertPostToPostDTO(post)).
                    collect(Collectors.toList());
    }


    @Override
    public PostDTO getPostbyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", Long.toString(id)));
        //orElseThrow(()-> new ResourceNotFoundException("post", "id", Long.toString(id)));
        //return convertPostToPostDTO(post);
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
