package com.example.contactmanager.model;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Indexed;

import ch.qos.logback.core.subst.Token.Type;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
@Document(collection = "user")
public class User {
     


    @Id
    ObjectId id;
    public ObjectId getId() {
        return id;
    }


    public void setId(ObjectId id) {
        this.id = id;
    }


    @NotBlank(message = "please enter name")
    private String name;
    
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = " email invalid")
    private String email;
  @NotEmpty( message = " set password")
  @Size(min=3, max = 10,message = "password must be 3 to 10 character")
    private String password;

    private String image;
   
    @NotEmpty(message = "please write about something")
    private String about;

    
    private boolean enable;
    @AssertTrue(message = "please check form")
    private boolean check;

    @DocumentReference(lazy = true)
    private Set <Contact> contacts;
    
   
   @DocumentReference(lazy = true)
    private Set<Role> roleId;





public String getName() {
    return name;
}


public void setName(String name) {
    this.name = name;
}


public String getEmail() {
    return email;
}


public void setEmail(String email) {
    this.email = email;
}


public String getPassword() {
    return password;
}


public void setPassword(String password) {
    this.password = password;
}


public String getImage() {
    return image;
}


public void setImage(String image) {
    this.image = image;
}


public String getAbout() {
    return about;
}


public void setAbout(String about) {
    this.about = about;
}


public boolean isEnable() {
    return enable;
}


public void setEnable(boolean enable) {
    this.enable = enable;
}


public boolean isCheck() {
    return check;
}


public void setCheck(boolean check) {
    this.check = check;
}


public Set<Contact> getContacts() {
    return contacts;
}


public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
}


public Set<Role> getRoleId() {
    return roleId;
}


public void setRoleId(Set<Role> roleId) {
    this.roleId = roleId;
}


@Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", image=" + image
                + ", about=" + about + ", enable=" + enable + ", check=" + check + ", contacts=" + contacts
                + ", roleId=" + roleId + "]";
    }

}
