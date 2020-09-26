package com.gss.chs.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHandler extends SQLiteOpenHelper {
    public static final String Database_Name = "Student.db";
    public static final String TABLE_NAME = "Student_Table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "IN_TIME";
    public static final String COL_3 = "OUT_TIME";
//    public static final String COL_4 = "PASSWORD";
//    public static final String COL_5 = "DOB";
//    public static final String COL_6 = "GENDER";
//    public static final String COL_7 = "COLDRINK";
//    public static final String COL_8 = "NOODLES";
//    public static final String COL_9 = "FRUITY";
//    public static final String COL_10 = "COUNTRY";

    public MyDbHandler(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"( ID TEXT PRIMARY KEY  ,IN_TIME TEXT,OUT_TIME)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertData(String user_id,String in_time ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,user_id);
        contentValues.put(COL_2, in_time);
//        contentValues.put(COL_3,Email);
//        contentValues.put(COL_4, password);
//        contentValues.put(COL_5,dob);
//        contentValues.put(COL_6,gender);
//        contentValues.put(COL_7, coldrink);
//        contentValues.put(COL_8,noodles);
//        contentValues.put(COL_9, fruity);
//        contentValues.put(COL_10,country);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }
    public boolean updateData(String out_time){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_3,out_time);
//        contentValues.put(COL_2,Name);
//        contentValues.put(COL_3,Email);
//        contentValues.put(COL_4,password);
//        contentValues.put(COL_5,dob);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] {out_time});
        return  true;
    }
    public Integer delete(String time){
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(TABLE_NAME,"ID=?",new String []{time});
    }
}