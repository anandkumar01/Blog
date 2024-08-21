package com.project.springboot.springbootproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springboot.springbootproject.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
