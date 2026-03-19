package com.example.learnxcompile.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class authModel extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "learnXcompile";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    // used this flag for testing !!
//    private static boolean isFirstStart = true;

    public authModel(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);

//        if(isFirstStart){
//            context.deleteDatabase(DATABASE_NAME);
//            isFirstStart = false;
//        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + "id integer primary key autoincrement," +
                "username text unique not null," +
                "email text unique not null," +
                "password text)";
        String sqlInsert = "INSERT INTO " + TABLE_NAME +
                "(username,email,password)"+
                " VALUES ('username','test@gmail.com', '"+
                BCrypt.hashpw("password", BCrypt.gensalt(10))+ "')";
        db.execSQL(sql);
        db.execSQL(sqlInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(String username,String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String password_hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        values.put("username", username);
        values.put("email", email);
        values.put("password", password_hash);
        long res = db.insert(TABLE_NAME, null, values);
        db.close();
        return res != -1;
    }
    public boolean checkUser(String nameOrEmail, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+
                " WHERE email= ? OR username= ? ";
        boolean isValid = false;
        Cursor cursor = db.rawQuery(sql, new String[]{nameOrEmail,nameOrEmail});
        boolean isExists = cursor.getCount() > 0;
        if(cursor.moveToFirst() && isExists){
            String password_hash = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if(BCrypt.checkpw(password, password_hash)){
                isValid = true;
            }
        }
        cursor.close();
        db.close();
        return isValid;
    }
    public boolean userExists(String username, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+
                " WHERE email= ? OR username= ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{username,email});
        return cursor.getCount() > 0;
    }

}

