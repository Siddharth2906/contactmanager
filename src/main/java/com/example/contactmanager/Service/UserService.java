package com.example.contactmanager.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contactmanager.Repositry.UserRepository;
import com.example.contactmanager.model.User;

@Service
public class UserService {
    @Autowired
private UserRepository us;

public User savedata(User u){
    return (User) us.save(u);
}

public Optional<User> findByEmail(String username) {
    return us.findByEmail(username);
}
}
