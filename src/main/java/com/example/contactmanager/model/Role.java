package com.example.contactmanager.model;



import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;



@Document
public class Role {
    @Id
    long id;
    String rname;
    
    @DocumentReference(lazy = true, lookup = "{ 'roleId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<User> name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Set<User> getName() {
        return name;
    }

    public void setName(Set<User> name) {
        this.name = name;
    }


}
