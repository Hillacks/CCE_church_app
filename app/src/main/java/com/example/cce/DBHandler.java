package com.example.cce;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbCCE";
    private static final String TABLE_NAME = "Members";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "fullName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, "dbCCE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + "TEXT,"
                + COLUMN_EMAIL + "VARCHAR,"
                + COLUMN_PASSWORD + "VARCHAR )";
        db.execSQL(query);
        this.db = db;

    }

    //upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //create tables again
        this.onCreate(db);

    }

    //adds new church members
    public boolean registerMember(String fullName, String email, String password) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, fullName); //member's full name
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert("Members", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean CheckEmail(String email) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Members WHERE email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean CheckLogin(String email, String password) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Members WHERE email=? AND password=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getChurchMember() {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PASSWORD},
                null, null, null, null, null);
        int eId   = cursor.getColumnIndex(COLUMN_ID);
        int eName = cursor.getColumnIndex(COLUMN_NAME);
        int eEmail = cursor.getColumnIndex(COLUMN_EMAIL);
        int ePasswd = cursor.getColumnIndex(COLUMN_PASSWORD);

        String res = "";

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            res = res +
                    "id: "+cursor.getString(eId)+"\n"+
                    "eName: "+cursor.getString(eName)+"\n"+
                    "eEmail:"+cursor.getString(eEmail)+"\n"+
                    "ePasswd: "+cursor.getString(ePasswd)+"\n";
        }
        db.close();
        return res;
    }
}




