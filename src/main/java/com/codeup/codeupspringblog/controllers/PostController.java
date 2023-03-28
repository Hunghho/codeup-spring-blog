package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    // Dependency Injection
    private PostRepository postsDoa;

    public PostController(PostRepository postsDoa) {
        this.postsDoa = postsDoa;
    }

    @GetMapping("/posts")
    public String returnPosts(Model model){

//        List<Post> posts = new ArrayList<>(Arrays.asList(
//                new Post(1, "First post", "My first post"),
//                new Post(2, "Second post", "My second post")
//        ));

        List<Post> posts = postsDoa.findAll();

        model.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/post/{id}")
    public String returnPost(@PathVariable long id, Model model) {

//        Post post = new Post(3, "First post", "My first post");

        Optional<Post> post = postsDoa.findById(2L);
        if(post.isPresent()){
            model.addAttribute("post", post);
        }


        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String returnPostCreateForm() {

       return "posts/create";
    }

    @PostMapping(value = "/posts/create")

    public String creatPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
        Post post = new Post(title, body);
        postsDoa.save(post);
        return "redirect:/posts";
    }

}
