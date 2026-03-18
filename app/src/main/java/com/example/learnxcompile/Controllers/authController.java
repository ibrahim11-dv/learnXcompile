package com.example.learnxcompile.Controllers;

import android.content.Context;

import com.example.learnxcompile.Models.authModel;

public class authController {
    authModel am;
    public authController( Context context){
        am = new authModel(context);
    }
    public boolean authenticate(String username, String password){
        return am.checkUser(username,password);
    }
    public boolean userExists(String username, String email){
        return am.userExists(username,email);
    }
    public void addUser(String username, String email, String password){
        am.addUser(username, email, password);
    }
}
