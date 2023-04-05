package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.model.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private UserRepository usersDoa;

    private PostRepository postsDoa;

    private UserRepository userDao;

    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDoa, PostRepository postsDoa, UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.usersDoa = usersDoa;
        this.postsDoa = postsDoa;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "sign-up";
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

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        User userLogIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userLogIn.getUsername());
        User user = usersDoa.findByUsername(userLogIn.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userPosts", postsDoa.findByUser(user));
        return "users/profile";
    }
}
