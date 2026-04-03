package com.example.learnxcompile.Controllers;

import android.content.Context;

import com.example.learnxcompile.Items.Language;
import com.example.learnxcompile.Models.ChapterModel;

import java.util.List;
import java.util.Map;

public class ChapterController {
    private ChapterModel model;

    public ChapterController(Context context){
        model = new ChapterModel(context);
    }

    public List<Language> getLanguages(){
        return model.getLanguages();
    }

    public Language getLanguageByName(String name) {
        return model.getLanguageByName(name);
    }

    // Explanation Content
    public String getExplanationText(int chapterId) {
        Map<String, String> content = model.getExplanationContent(chapterId);
        return content.getOrDefault("text", "No content available.");
    }

    public String getDidYouKnow(int chapterId) {
        Map<String, String> content = model.getExplanationContent(chapterId);
        return content.getOrDefault("did_you_know", "");
    }

    public String getKeyPoints(int chapterId) {
        Map<String, String> content = model.getExplanationContent(chapterId);
        return content.getOrDefault("key_points", "");
    }

    // Code Example Content
    public Map<String, String> getCodeContent(int chapterId) {
        return model.getCodeContent(chapterId);
    }

    // QCM Logic
    public String getQuestionForChapter(int chapterId) {
        Map<String, String> content = model.getQcmContent(chapterId);
        return content.getOrDefault("question", "Question not found.");
    }

    public String[] getOptionsForChapter(int chapterId) {
        Map<String, String> content = model.getQcmContent(chapterId);
        return new String[]{
            content.getOrDefault("option_a", ""),
            content.getOrDefault("option_b", ""),
            content.getOrDefault("option_c", ""),
            content.getOrDefault("option_d", "")
        };
    }

    public int getCorrectOptionIndex(int chapterId) {
        Map<String, String> content = model.getQcmContent(chapterId);
        String correct = content.getOrDefault("correct", "A");
        switch (correct) {
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return 0;
        }
    }

    public String getExplanationForChapter(int chapterId) {
        Map<String, String> content = model.getQcmContent(chapterId);
        return content.getOrDefault("explanation", "");
    }

    // Test Logic
    public String getTestInstructions(int chapterId) {
        Map<String, String> content = model.getTestContent(chapterId);
        return content.getOrDefault("instructions", "");
    }

    public String getExpectedOutput(int chapterId) {
        Map<String, String> content = model.getTestContent(chapterId);
        return content.getOrDefault("expected_output", "");
    }

    public String getStarterCode(int chapterId) {
        Map<String, String> content = model.getTestContent(chapterId);
        return content.getOrDefault("starter_code", "");
    }

    public String getHint(int chapterId) {
        Map<String, String> content = model.getTestContent(chapterId);
        return content.getOrDefault("hint", "No hint available for this step.");
    }

    public void completeChapter(int chapterId) {
        model.unlockNextChapter(chapterId);
    }

    public void unlockAllForLanguage(String languageName) {
        model.unlockAllChapters(languageName);
    }
}
