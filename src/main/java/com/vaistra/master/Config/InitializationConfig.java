package com.vaistra.master.Config;

import com.vaistra.master.entity.User;
import com.vaistra.master.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitializationConfig {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitializationConfig(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeAdminUser() {
        User user = userRepository.findByEmailIgnoreCase("admin@gmail.com");

        if (user == null) {
            user = new User();

            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setName("Admin");
            userRepository.save(user);
        }
    }

}
