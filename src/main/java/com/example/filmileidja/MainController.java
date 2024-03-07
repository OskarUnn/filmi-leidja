package com.example.filmileidja;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/filmid")
    public String filmid(Model model) {
        return "filmid";
    }
}
