package se.systementor.javasecstart.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private final UserService userService;

    @RequestMapping(path = "")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(path = "/reg")
    public String regPage(){
        return "newuser";
    }

    @RequestMapping(path = "/regReciever")
    public String regRecieverPage(@RequestParam String username, @RequestParam String password, Model model){
        userService.addUser(username, password);
        return "login";
    }

}
