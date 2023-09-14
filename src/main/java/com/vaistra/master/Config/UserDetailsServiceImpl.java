package com.vaistra.master.Config;

import com.vaistra.master.entity.User;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User not found...!"));
        return user;
    }

}
