package com.example.contactmanager;


import java.util.Arrays;
import java.util.List;
public class Roled {
    
    public static String getRole(List<Integer> roleIds) {
        if (roleIds.contains(10001) && roleIds.contains(10000)) {
            return "Admin,User";
        } else if (roleIds.contains(10001)) {
            return "Admin";
        } else if (roleIds.contains(10000)) {
            return "User";
        } else {
            return "Unknown Role";
        }
    }
}

