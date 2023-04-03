package com.codeup.codeupspringblog.model;

import java.util.HashSet;
import java.util.Set;

public class Categories {

    public static Set<Category> makeCatSet(String catsCsv){
        Set<Category> catObjects = new HashSet<>();

        if(catsCsv.equals("")){
            return catObjects;
        }

        for (String cat : catsCsv.split(",")){
            Category catObject = new Category(cat.trim());
            catObjects.add(catObject);
        }

        return catObjects;
    }
}
