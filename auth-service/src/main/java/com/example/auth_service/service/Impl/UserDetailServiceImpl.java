package com.example.auth_service.service.Impl;

import com.example.auth_service.entity.User;
import com.example.auth_service.handle.CustomRunTimeException;
import com.example.auth_service.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> myUser = Optional.ofNullable(userRepository.findUserByUsername(username));

        if(myUser.isPresent()){
            var userObj = myUser.get();


            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(myUser.get().getRole().toString()).build();

        }
        else {
            throw new CustomRunTimeException("User not found!");
        }
    }
}
