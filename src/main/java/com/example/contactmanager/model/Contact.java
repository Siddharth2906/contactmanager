package com.example.contactmanager.model;

import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Document(collection = "contacts")
public class Contact {
    @Id
    ObjectId cid;

    @NotBlank(message = "please enter name")
    private String cname;
    @NotBlank(message = "please enter nickname")
    private String nickname;
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = " email invalid")
    private String email;
    @Digits(integer = 10,fraction = 0,message = "please enter valid number")
    private long mobile_no;
    @NotEmpty(message = "please write work")
    private String work;
    private String cimage;
    @NotEmpty(message = "please write about something")
    private String description;
   

    @DocumentReference(lazy = true, lookup = "{ 'contacts' : ?#{#self._id} }")
    private User user;

   
    public ObjectId getCid() {
        return cid;
    }
    public void setCid(ObjectId cid) {
        this.cid = cid;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getMobile_no() {
        return mobile_no;
    }
    public void setMobile_no(long mobile_no) {
        this.mobile_no = mobile_no;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getCimage() {
        return cimage;
    }
    public void setCimage(String cimage) {
        this.cimage = cimage;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Contact [cid=" + cid + ", cname=" + cname + ", nickname=" + nickname + ", email=" + email
                + ", mobile_no=" + mobile_no + ", work=" + work + ", cimage=" + cimage + ", description=" + description
                + ", user=" + user + "]";
    }




    
}
