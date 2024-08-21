package se.systementor.javasecstart.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.security.ConcreteUserDetails;
import se.systementor.javasecstart.security.User;
import se.systementor.javasecstart.security.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return new ConcreteUserDetails(user);
    }

    public void addUser(String mail, String password){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        User user = User.builder().enabled(true).password(hash).username(mail).build();
        userRepository.save(user);
    }
}
