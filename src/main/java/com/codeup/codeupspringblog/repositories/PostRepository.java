package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.model.Post;
import com.codeup.codeupspringblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id);

    List<Post> findByUser(User user);
}
