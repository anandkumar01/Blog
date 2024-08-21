package com.project.springboot.springbootproject.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.springboot.springbootproject.models.Account;
import com.project.springboot.springbootproject.models.Authority;
import com.project.springboot.springbootproject.models.Post;
import com.project.springboot.springbootproject.services.AccountService;
import com.project.springboot.springbootproject.services.AuthorityService;
import com.project.springboot.springbootproject.services.PostService;
import com.project.springboot.springbootproject.util.constants.Privillages;
import com.project.springboot.springbootproject.util.constants.Roles;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

        for (Privillages privillage : Privillages.values()) {
            Authority authority = new Authority();
            authority.setId(privillage.getId());
            authority.setName(privillage.getPrivillage());
            authorityService.save(authority);
        }

        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();
        Account account04 = new Account();

        account01.setEmail("anand.user@gmail.com");
        account01.setFirstname("Anand");
        account01.setLastname("Kumar");
        account01.setPassword("anand123");

        account02.setEmail("abhinav.admin@gmail.com");
        account02.setFirstname("Abhinav");
        account02.setLastname("Singhal");
        account02.setPassword("abhi123");
        account02.setRole(Roles.ADMIN.getRole());

        account03.setEmail("ayush.editor@gmail.com");
        account03.setFirstname("Ayush");
        account03.setLastname("Ranjan");
        account03.setPassword("ayush123");
        account03.setRole(Roles.EDITOR.getRole());

        account04.setEmail("pawan.supereditor@gmail.com");
        account04.setFirstname("Pawan");
        account04.setLastname("Mishra");
        account04.setPassword("pawan123");
        account04.setRole(Roles.EDITOR.getRole());

        Set<Authority> authorities = new HashSet<>();
        authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        account04.setAuthorities(authorities);

        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);

        List<Post> posts = postService.getAll();
        if (posts.size() == 0) {
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("Post 01 body.........");
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("Post 02 body........");
            post02.setAccount(account02);
            postService.save(post02);
        }
    }

}
