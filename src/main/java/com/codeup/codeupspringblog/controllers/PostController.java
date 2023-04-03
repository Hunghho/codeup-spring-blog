package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.model.User;
import com.codeup.codeupspringblog.repositories.CatsRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    // Dependency Injection
    private PostRepository postsDoa;

    private UserRepository usersDao;

    private CatsRepository catsDao;

    private final EmailService emailService;

    public PostController(PostRepository postsDoa, UserRepository usersDao, CatsRepository catsDao, EmailService emailService) {
        this.postsDoa = postsDoa;
        this.usersDao = usersDao;
        this.catsDao = catsDao;
        this.emailService = emailService;
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
    public String returnPostCreateForm(Model model) {
        model.addAttribute("post", new Post());
       return "posts/create";
    }

    @PostMapping(value = "/posts/create")
    public String creatPost(@ModelAttribute Post post) {
//        Set<Category> catSet = Categories.makeCatSet(cats);
//        Post newPost = new Post(post.getTitle(), post.getBody(), usersDao.findById(1L));
//        if(catSet.size() > 0){
//            List<Category> catsToAdd = new ArrayList<>();
//            for(Category cat : catSet){
//                Category catFromDb = catsDao.findCatByName(cat.getName());
//                if(catFromDb == null){
//                    catsToAdd.add(catsDao.save(cat));
//                } else {
//                    catsToAdd.add(catFromDb);
//                }
//            }
//            catSet.clear();
//            catSet.addAll(catsToAdd);
//            post.setCats(catSet);
//        }
        //hard coding user
        User user = usersDao.findById(1L);
        post.setUser(user);
        //saving post
        postsDoa.save(post);
        emailService.prepareAndSend(post, "Your post have been created");
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditPostForm(@PathVariable long id, Model model) {
        Post post = postsDoa.findById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@ModelAttribute Post post, @PathVariable long id){
        Post editPost = postsDoa.findById(id);
        editPost.setTitle(post.getTitle());
        editPost.setBody(post.getBody());
//        post = new Post(id, post.getTitle(), post.getBody());
//        post.setUser(usersDao.findById(2L));
        postsDoa.save(editPost);
        return "redirect:/posts";
    }

}
