package com.blogfortraining.restapi.entity;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Annotations
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
//titles must be different
@Table(
    name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames={"title"})}
)
public class Post {
/*
    +-------------+--------------+------+-----+---------+----------------+
    | Field       | Type         | Null | Key | Default | Extra          |
    +-------------+--------------+------+-----+---------+----------------+
    | id          | bigint(20)   | NO   | PRI | NULL    | auto_increment |
    | content     | varchar(255) | NO   |     | NULL    |                |
    | description | varchar(255) | NO   |     | NULL    |                |
    | title       | varchar(255) | NO   | UNI | NULL    |                |
    +-------------+--------------+------+-----+---------+----------------+
*/
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
