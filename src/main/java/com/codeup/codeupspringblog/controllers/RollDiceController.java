package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String returnRollDicePage() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    public String diceResult (Model model, @PathVariable int n) {
        Random random = new Random();
        int randomNum = random.nextInt((6-1) + 1) + 1;
        model.addAttribute("number", n);
        model.addAttribute("random", randomNum);

        if (n == randomNum){
            model.addAttribute("message", "You got it right!");
        } else {
            model.addAttribute("message", "You got it wrong!");
        }
        return "roll-dice-result";
    }
}
