package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    List<Post> posts = new ArrayList<>(Arrays.asList(
            new Post( 1, "First post", "My first post"),
            new Post( 2, "Second post", "My second post")
    ));

    @GetMapping("/posts")
    public String returnPosts(Model model){

        model.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/post/{id}")
    public String returnPost(@PathVariable long id, Model model) {

//        Post post = new Post("First post", "My first post");

        model.addAttribute("postId", id);
        model.addAttribute("post", posts.get(0));

        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String returnPostCreateForm() {
        return "view post form";
    }

    @PostMapping(value = "/posts/create")
    @ResponseBody
    public String creatPost(@RequestParam String body) {
        return body;
    }

}
