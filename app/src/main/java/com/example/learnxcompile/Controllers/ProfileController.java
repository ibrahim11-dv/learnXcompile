package com.example.learnxcompile.Controllers;

import android.content.Context;
import com.example.learnxcompile.Models.authModel;
import java.util.Map;

public class ProfileController {
    private authModel model;

    public ProfileController(Context context) {
        model = new authModel(context);
    }

    public Map<String, String> getUserProfile(String usernameOrEmail) {
        return model.getUserDetails(usernameOrEmail);
    }

    public boolean updatePassword(String username, String newPassword) {
        return model.updatePassword(username, newPassword);
    }

    public boolean updateUsername(String oldUsername, String newUsername) {
        return model.updateUsername(oldUsername, newUsername);
    }
}
