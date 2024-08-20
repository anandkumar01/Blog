package com.project.springboot.springbootproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springboot.springbootproject.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
