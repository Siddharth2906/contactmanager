package com.example.contactmanager.Repositry;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.contactmanager.model.User;


@Repository
public interface UserRepository extends MongoRepository<User,ObjectId>{

    public Optional<User> findByEmail(String email);

    
}
