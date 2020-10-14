package com.deni.springsecurity.service;


import com.deni.springsecurity.entitas.User;
import com.deni.springsecurity.entitas.UserPrincipal;
import com.deni.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = userRepository.findByName(s);
                if(user != null)
                        return new UserPrincipal(user);
        else return new UserPrincipal(new User());
    }
}
