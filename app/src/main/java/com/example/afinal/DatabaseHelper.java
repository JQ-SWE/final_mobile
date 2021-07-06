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
        myDB.execSQL("CREATE TABLE BIKEACCOUNT(PHONE TEXT primary key, RENTALNO TEXT, RENTALHOUR TEXT, BALANCE TEXT)");
    }

    //drop the table if already exist
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("DROP TABLE IF EXISTS USERS");
        myDB.execSQL("DROP TABLE IF EXISTS BIKEACCOUNT");
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

    public Cursor getQuestion(String phoneNumber) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE PHONENUMBER = ?", new String[]{phoneNumber});
        return cursor;
    }

    public Boolean updatePassword(String phoneNumber, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = myDB.update("users", contentValues, "phoneNumber = ?", new String[] {phoneNumber});
        if(result == -1) return false;
        else
            return true;
    }

    //FOR BIKEACCOUNT
    public Boolean topup(String topup) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM BIKEACCOUNT WHERE PHONE = ?", new String[]{topup});
        int mbalance = cursor.getInt(cursor.getColumnIndex("BALANCE"));

        ContentValues contentValues = new ContentValues();
        contentValues.put("BALANCE", topup+mbalance);
        long result = myDB.update("BIKEACCOUNT", contentValues, "phone = ?", new String[]{topup});
        if(result == -1) return false;
        else
            return true;
    }

    public Cursor getrentalinfo(String phone) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM BIKEACCOUNT WHERE PHONE = ?", new String[]{phone});
        return cursor;
    }

    public Boolean updaterentalno() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM BIKEACCOUNT WHERE PHONE = ?", new String[]{});
        int mrentalno = cursor.getInt(cursor.getColumnIndex("RENTALNO"));

        ContentValues contentValues = new ContentValues();
        contentValues.put("RENTALNO", 1+mrentalno);
        long result = myDB.update("BIKEACCOUNT", contentValues, "phone = ?", new String[]{});
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean updaterentalhour(String time) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM BIKEACCOUNT WHERE PHONE = ?", new String[]{});
        int mtime = cursor.getInt(cursor.getColumnIndex("RENTALHOUR"));

        ContentValues contentValues = new ContentValues();
        contentValues.put("RENTALHOUR", time+mtime);
        long result = myDB.update("BIKEACCOUNT", contentValues, "phone = ?", new String[]{});
        if(result == -1) return false;
        else
            return true;
    }

}