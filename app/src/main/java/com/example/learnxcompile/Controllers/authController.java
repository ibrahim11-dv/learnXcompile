package com.example.learnxcompile.Controllers;

import android.content.Context;

import com.example.learnxcompile.Models.authModel;

public class authController {
    authModel am;
    public authController( Context context){
        am = new authModel(context);
    }
    public boolean authentificate(String username, String password){
        return am.checkUser(username,password);
    }
}
