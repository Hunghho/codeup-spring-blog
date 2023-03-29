package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.model.User;
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
public class UserController {

    private UserRepository usersDoa;

    private PostRepository postsDoa;

    public UserController(UserRepository usersDao, PostRepository postsDoa) {
        this.usersDoa = usersDao;
        this.postsDoa = postsDoa;
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        User user = new User(username, email, password);
        usersDoa.save(user);
        return "redirect:/posts";
    }

    @GetMapping("/user/{id}/posts")
    public String userPosts(@PathVariable long id, Model model){
        User user = usersDoa.findById(id);
        if(user != null){
            List<Post> userPosts = user.getPosts();
            model.addAttribute("userPosts", userPosts);
            return "/posts/userPosts";
        } else {
            return "redirect:/posts";
        }

    }


}
