package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatsRepository extends JpaRepository<Category, Long> {

    Category findCatByName(String name);
}
