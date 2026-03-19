package com.example.learnxcompile.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.learnxcompile.Items.Language;

import java.util.ArrayList;
import java.util.List;

public class ChapterModel extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "learnXcompile";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "chapters";
    private static boolean isFirstStart = true;

    public ChapterModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(isFirstStart){
            context.deleteDatabase(DATABASE_NAME);
            isFirstStart = false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating tables
        String languagesSql = "CREATE TABLE languages" +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT)";

        String chaptersSql = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "language_id INTEGER," +
                "title TEXT," +
                "description TEXT," +
                "order_index INTEGER," +
                "is_locked INTEGER," +
                "FOREIGN KEY (language_id) REFERENCES languages(id))";

        db.execSQL(languagesSql);
        db.execSQL(chaptersSql);

        // Languages
        db.execSQL("INSERT INTO languages (name, description) VALUES ('Java', 'A class-based, object-oriented programming language')");
        db.execSQL("INSERT INTO languages (name, description) VALUES ('Python', 'A high-level, interpreted programming language')");

        // Java chapters (language_id = 1)
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (1, 'Introduction to Java', 'What is Java, JVM, and your first program', 1, 0)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (1, 'Variables & Data Types', 'int, double, String, boolean and type casting', 2, 1)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (1, 'Control Flow', 'if/else, switch, loops', 3, 1)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (1, 'Methods', 'Defining and calling methods, parameters, return types', 4, 1)");

        // Python chapters (language_id = 2)
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (2, 'Introduction to Python', 'What is Python and your first script', 1, 0)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (2, 'Variables & Data Types', 'int, float, str, bool and type conversion', 2, 1)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (2, 'Control Flow', 'if/elif/else, for and while loops', 3, 1)");
        db.execSQL("INSERT INTO chapters (language_id, title, description, order_index, is_locked) VALUES (2, 'Functions', 'def, parameters, return, lambda', 4, 1)");
    }

    public List<Language> getLanguages() {
        ArrayList<Language> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM languages", null);

        while (cursor.moveToNext()) {
            list.add(getLanguageFromCursor(cursor, db));
        }
        cursor.close();
        db.close();
        return list;
    }

    public Language getLanguageByName(String languageName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM languages WHERE name = ?", new String[]{languageName});
        Language language = null;
        if (cursor.moveToFirst()) {
            language = getLanguageFromCursor(cursor, db);
        }
        cursor.close();
        db.close();
        return language;
    }

    private Language getLanguageFromCursor(Cursor cursor, SQLiteDatabase db) {
        int langId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

        List<Language.Chapter> chapterList = new ArrayList<>();
        Cursor chapterCursor = db.rawQuery("SELECT * FROM chapters WHERE language_id = ? ORDER BY order_index", new String[]{String.valueOf(langId)});
        
        int totalChapters = 0;
        int completedChapters = 0;

        while (chapterCursor.moveToNext()) {
            int chId = chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("id"));
            String chTitle = chapterCursor.getString(chapterCursor.getColumnIndexOrThrow("title"));
            String chDesc = chapterCursor.getString(chapterCursor.getColumnIndexOrThrow("description"));
            boolean isLocked = chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("is_locked")) == 1;
            int order = chapterCursor.getInt(chapterCursor.getColumnIndexOrThrow("order_index"));

            Language.Chapter chapter = new Language.Chapter(chId, chTitle, chDesc, isLocked, order);
            chapterList.add(chapter);
            
            totalChapters++;
            if (!isLocked) completedChapters++;
        }
        chapterCursor.close();

        int progress = (totalChapters > 0) ? (completedChapters * 100 / totalChapters) : 0;

        Language l = new Language(langId, name, description, progress, completedChapters, totalChapters);
        l.chapters = chapterList;
        return l;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS languages");
        onCreate(sqLiteDatabase);
    }
}
