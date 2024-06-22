package com.example.contactmanager.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.contactmanager.model.Role;
@Repository
public interface RoleRepository extends MongoRepository<Role,Long>{

    
//    Optional <Role> findById(long id);

    Role save(Role role);
    boolean existsByNameIgnoreCase(String name);
    
}