package com.blogfortraining.restapi.repository;

import com.blogfortraining.restapi.entity.Post;

import org.springframework.data.repository.CrudRepository;

/* JPA Repository for post entity */ 
public interface PostRepository extends CrudRepository<Post, Long>{
    
}
