package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    // Dependency Injection
    private PostRepository postsDoa;

    private UserRepository usersDao;

    public PostController(PostRepository postsDoa, UserRepository usersDao) {
        this.postsDoa = postsDoa;
        this.usersDao = usersDao;
    }

    @GetMapping("/posts")
    public String returnPosts(Model model){

        List<Post> posts = postsDoa.findAll();

        model.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/post/{id}")
    public String returnPost(@PathVariable long id, Model model) {

        Post post = postsDoa.findById(id);
        if(post != null){
            model.addAttribute("post", post);
            model.addAttribute("userEmail", usersDao.findById(id));
            return "posts/show";
        } else {
            return "redirect:/posts";
        }
    }

    @GetMapping("/posts/create")
    public String returnPostCreateForm() {

       return "posts/create";
    }

    @PostMapping(value = "/posts/create")

    public String creatPost(@RequestParam String title, @RequestParam String body) {
        Post post = new Post(title, body, usersDao.findById(1L));
        postsDoa.save(post);
        return "redirect:/posts";
    }

}
