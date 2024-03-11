package com.example.filmileidja;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final SeanssRepository seanssRepository;

    public MainController(SeanssRepository seanssRepository) {
        this.seanssRepository = seanssRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/filmid")
    public String filmid(Model model) {
        return "filmid";
    }

    @GetMapping("/kinokava")
    public String kinokava(Model model) {
        return "kinokava";
    }

    @GetMapping("/valikoht/{id}")
    public String valikoht(Model model, @PathVariable("id") String id, @RequestParam int kohti) {
        model.addAttribute("seanssID", id);
        model.addAttribute("kohtadeArv", kohti);
        return "valikoht";
    }
}
