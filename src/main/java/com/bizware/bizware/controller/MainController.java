package com.bizware.bizware.controller;

import com.bizware.bizware.model.Visitor;
import com.bizware.bizware.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class MainController {
    @Autowired
    private VisitorRepository visitorRepository;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home"; //
    }

    @PostMapping("/greet")
    @ResponseBody
    public String greet(@RequestParam String name) {
        visitorRepository.save(new Visitor(name));
        return "Hello <strong>" + name + "!</strong>";
    }

    @GetMapping("/greetings")
    public String greetings(Model model) {
        model.addAttribute("visitorsList", visitorRepository.findAll());
        return "greetings";
    }

    @DeleteMapping("/deleteVisitor/{id}")
    public String deleteVisitor(@PathVariable("id") Integer id, Model model) {
        visitorRepository.deleteById(id);
        model.addAttribute("visitorsList", visitorRepository.findAll());
        return "visitors_table_fragment";
    }
}
