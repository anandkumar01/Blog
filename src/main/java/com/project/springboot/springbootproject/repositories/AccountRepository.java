package com.project.springboot.springbootproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.springboot.springbootproject.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.authorities WHERE a.email = :email")
    Optional<Account> findOneByEmailIgnoreCase(@Param("email") String email);
}
