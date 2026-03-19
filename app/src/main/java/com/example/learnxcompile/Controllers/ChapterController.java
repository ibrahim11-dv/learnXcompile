package com.example.learnxcompile.Controllers;

import android.content.Context;

import com.example.learnxcompile.Items.Language;
import com.example.learnxcompile.Models.ChapterModel;

import java.util.List;

public class ChapterController {
    ChapterModel model;
    public ChapterController(Context context){
        model= new ChapterModel(context);
    }
    public List<Language> getLanguages(){
        return model.getLanguages();
    }
}
