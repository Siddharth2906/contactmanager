package com.example.contactmanager.Repositry;

import org.springframework.stereotype.Repository;

import com.example.contactmanager.model.Contact;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import com.example.contactmanager.model.User;




@Repository
public interface ContactRepo extends MongoRepository<Contact,ObjectId>{
    

    // @Query("form Contact as c where c.cid:Id")
    // public Page<Contact> findContactsByUser(ObjectId userId,PageRequest p);
    
    //  public Page <Contact> findByCid(ObjectId cid);
    // @Query("{ 'user': userId }")
    // Page<Contact> findContactsByUser(ObjectId userId,PageRequest p);
     
    // @Query("{ 'user': ?0 }")
    //  Page <Contact> findContactsByUser(ObjectId user,PageRequest p);
    //  @Query(" {'user.id' : 'userId'} ")
    //  public Page<Contact> findContactsByUser( ObjectId userId,PageRequest p);

    // Page<Contact> 
// public Page<Contact> f( ObjectId userId,PageRequest p);
   
//    public Page <Contact> findByUser(User user ,PageRequest p);

@Query("form Contact as c where c.cid:Id")
Page <Contact> findAllContactsByUserId(ObjectId userId,PageRequest p);

Page <Contact> findByUser(User user ,PageRequest p);
 
}
