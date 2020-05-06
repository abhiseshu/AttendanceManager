package com.example.attendancemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "ATTENDDB";
    private static int DB_VER = 1;
    private static String DB_TABLE = "AMTABLE";
    private static String DB_C_SUBJECT = "SUBJECTS";
    private static String DB_C_PRESENT = "NOOFPRESENTS";
    private static String DB_C_TOTAL = "NOOFTOTAL";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL,%s INTEGER DEFAULT 0,%s INTEGER DEFAULT 0);", DB_TABLE, DB_C_SUBJECT, DB_C_PRESENT, DB_C_TOTAL);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertSubject(String mSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_C_SUBJECT, mSubject);
        db.insertWithOnConflict(DB_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void insertPresent(int mPresent,String mSubject){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_C_PRESENT,mPresent);
        db.update(DB_TABLE,contentValues,DB_C_SUBJECT+"=?",new String[]{mSubject});

        db.close();
    }

    public void insertTotal(int mTotal,String mSubject){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_C_TOTAL,mTotal);
        db.update(DB_TABLE,contentValues,DB_C_SUBJECT+"=?",new String[]{mSubject});
        db.close();
    }

    public void deleteSubject(String mSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_C_SUBJECT+"=?",new String[]{mSubject});
        db.close();
    }
    public ArrayList<Word> getSubjectList()
    {
        ArrayList<Word> SubjectList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_C_SUBJECT,DB_C_PRESENT,DB_C_TOTAL},null,null,null,null,null);
        while(cursor.moveToNext()){
            int wSubject = cursor.getColumnIndex(DB_C_SUBJECT);
            int wPresent = cursor.getColumnIndex(DB_C_PRESENT);
            int wtotal = cursor.getColumnIndex(DB_C_TOTAL);
            SubjectList.add(new Word(cursor.getString(wSubject),cursor.getString(wPresent),cursor.getString(wtotal)));
        }
            return  SubjectList;
    }

}
