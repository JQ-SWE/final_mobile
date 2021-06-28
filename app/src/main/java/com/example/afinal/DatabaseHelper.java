package com.example.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //create database
    public static final String DBNAME = "login.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    //create the table on database
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE USERS(certificateType TEXT, certificateNumber TEXT, name TEXT, phoneNumber TEXT primary key, location TEXT, password TEXT, securityQuestion TEXT, securityAnswer TEXT)");

    }

    //drop the table if already exist
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("DROP TABLE IF EXISTS USERS");
    }

    //to insert data
    public Boolean insertData(String certificateType, String certificateNumber, String name, String phoneNumber, String location, String password, String securityQuestion, String securityAnswer) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("certificateType", certificateType);
        contentValues.put("certificateNumber", certificateNumber);
        contentValues.put("name", name);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("location", location);
        contentValues.put("password", password);
        contentValues.put("securityQuestion", securityQuestion);
        contentValues.put("securityAnswer", securityAnswer);
        long result = myDB.insert("users", null, contentValues);
        if (result == 1) return false;
        else
            return true;
    }

    //to check the phone
    public Boolean checkUser(String phoneNumber) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE PHONENUMBER = ?", new String[]{phoneNumber});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //to check password
    public Boolean checkPhoneNumberPassword(String phoneNumber, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE PHONENUMBER = ? AND PASSWORD = ?", new String[]{phoneNumber, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUserAnswer(String phoneNumber, String answer) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE PHONENUMBER = ? AND securityAnswer = ?", new String[]{phoneNumber, answer});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public void changePassword(String phone,String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("UPDATE USER SET PASSWORD =? where phone =?", new String[]{phone, password});
    }

    public Cursor getQuestion(String phoneNumber) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE PHONENUMBER = ?", new String[]{phoneNumber});
        return cursor;
    }
}