package com.example.learnxcompile.Items;

import java.util.ArrayList;
import java.util.List;

public class Language {
    public int id;
    public String name;
    public String description;
    public int progress;
    public int chapterCompleted;
    public int chapterCount;
    public List<Chapter> chapters;

    public Language(int id, String name, String description, int progress, int chapterCompleted, int chapterCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.chapterCompleted = chapterCompleted;
        this.chapterCount = chapterCount;
        this.chapters = new ArrayList<>();
    }

    public static class Chapter {
        public int id;
        public String title;
        public String description;
        public boolean isLocked;
        public int order;

        public Chapter(int id, String title, String description, boolean isLocked, int order) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.isLocked = isLocked;
            this.order = order;
        }
    }
}
