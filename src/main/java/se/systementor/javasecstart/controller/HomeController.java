package se.systementor.javasecstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    @RequestMapping(path="/")
    String empty(Model model)
    {
        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

//        model.addAttribute("dogs", dogRepository.findAll());
        return "home";
    }}
