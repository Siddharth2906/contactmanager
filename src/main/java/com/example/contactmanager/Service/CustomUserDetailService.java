package com.example.contactmanager.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.contactmanager.Repositry.UserRepository;
import com.example.contactmanager.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("i am in find email");

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            System.out.println(username);
            return buildUserDetails(user.get());
        } else {
            System.out.println("inavalid");
          
            throw new UsernameNotFoundException("Invalid Username or password");

        }
    }

    private UserDetails buildUserDetails(User user) {
        System.out.println("inside password");
        List<GrantedAuthority> authorities = user.getRoleId().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRname())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

}
