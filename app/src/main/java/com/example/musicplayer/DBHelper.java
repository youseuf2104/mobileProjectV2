package com.example.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import java.util.ArrayList;
import java.util.HashMap;


import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    //Name of database
    public static final String DATABASE_NAME = "music.db";
    //Name of table
    public static final String CONTACTS_TABLE_NAME = "accounts";
    //Name of "contact" table columns
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_USERNAME = "Username";
    public static final String CONTACTS_COLUMN_EMAIL = "Email";
    public static final String CONTACTS_COLUMN_PASSWORD = "Password";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db){
        /*
        db.execSQL("create table contacts"+
                        "(id integer primary key AUTOINCREMENT, name text NOT NULL)"
        );  */

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME  +
                "(" +
                CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                CONTACTS_COLUMN_USERNAME + " text NOT NULL," +
                CONTACTS_COLUMN_EMAIL + " text NOT NULL," +
                CONTACTS_COLUMN_PASSWORD + " text NOT NULL " + //Make name column
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE ); //Make the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertAccount (String username,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        /* Each Content Values object represents a single table row */
        ContentValues contentValues = new ContentValues();
        //Putting the name String value into the "name" column
        contentValues.put("Username", username);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        db.insert("accounts", null, contentValues);
        return true;
    }


    public Boolean checkUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from accounts where username= ?", new String[]{username});
        if (res.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from accounts where email= ?", new String[]{email});
        if (res.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkLogin(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from accounts where username= ? and password =?", new String[]{username,password});
        if (res.getCount()>0)
            return true;
        else
            return false;

    }









    public boolean updateSong (Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Update the "name" column value to (parameter) name in the row with the (parameter) id
        contentValues.put("name", name);
        db.update("accounts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("accounts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }









}