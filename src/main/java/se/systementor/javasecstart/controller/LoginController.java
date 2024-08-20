package se.systementor.javasecstart.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/login")
public class LoginController {

    @RequestMapping(path = "")
    public String loginPage(){
        return "login";
    }

}
