package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.services.HashService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping(path = "/hash")
public class HashController {

    @Autowired
    HashService hashService;

    @RequestMapping(path = "")
    public String HashPage(){
        return "HashDemo";
    }

    @PostMapping("/submit-password")
    public String submitPassword(@RequestParam("passwords") String passwords, Model model) throws IOException, NoSuchAlgorithmException {

        String dictFilePath = "passwords.dict";
        hashService.writeToFile(dictFilePath, passwords);

        String hashFilePath = "hashes.txt";
        hashService.generateHash(dictFilePath, hashFilePath);

        model.addAttribute("message", "Nya hashar genererades.");

        return "HashDemo";
    }

    @RequestMapping(path = "/hash-crack")
    public String hashCrackPage(){
        return "crackHash";
    }

    @PostMapping("/submit-hash")
    public String submitHash(@RequestParam("hash") String hash, Model model) {

        String password = hashService.getPassword(hash);
        model.addAttribute("password", "Lösenordet är: " + password);

        return "crackHash";
    }


}
