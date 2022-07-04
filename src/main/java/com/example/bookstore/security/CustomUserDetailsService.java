package com.example.bookstore.security;

import com.example.bookstore.user.UserDto;
import com.example.bookstore.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsManager {

    UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        return new CustomUserDetails(user.getUserName(), user.getPassword(), user.getRole(), user.isActive());
    }

    @Override
    public void createUser(UserDetails user) {}

    @Override
    public void updateUser(UserDetails user) {}

    @Override
    public void deleteUser(String username) {}

    @Override
    public void changePassword(String oldPassword, String newPassword) {}

    @Override
    public boolean userExists(String username) {
        return userRepository.existsUserDtoByUserName(username);
    }
}
