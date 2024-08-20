package se.systementor.javasecstart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;

    public void seed() {
        if (userRepository.getUserByUsername("admin@hotmail.com") == null) {
            addUser("admin@hotmail.com");
        }
    }

    private void addUser(String mail){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Hejsan123#");
        User user = User.builder().enabled(true).password(hash).username(mail).build();
        userRepository.save(user);
    }

}
