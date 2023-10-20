package com.example.joblisting.controller;

import com.example.joblisting.repository.PostRepository;
import com.example.joblisting.model.Post;
import javax.servlet.http.HttpServletResponse;

import com.example.joblisting.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository repo;

    @Autowired
    SearchRepository searchRepository;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");


    }
    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return repo.findAll();

    }

    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text){
        return searchRepository.findByText(text);
    }



    @PostMapping("/post")
    public Post addPost(@RequestBody Post post){
        return repo.save(post);
    }


}
