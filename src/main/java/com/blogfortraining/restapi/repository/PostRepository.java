package com.blogfortraining.restapi.repository;

import com.blogfortraining.restapi.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
/* JPA Repository for post entity */ 
public interface PostRepository extends JpaRepository<Post, Long>{
    
}
