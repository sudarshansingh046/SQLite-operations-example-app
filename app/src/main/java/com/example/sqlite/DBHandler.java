package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME="demodb";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="record";
    private static final String ID_COL="id";
    private static final String NAME_COL="name";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+ID_COL+
                " INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME_COL+" TEXT)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create table again
        onCreate(db);
    }
    public void insertRecord(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,name);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public String getRecords(){
        String query="SELECT * FROM "+TABLE_NAME;
        String result="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            result+=cursor.getString(0)+" "+cursor.getString(1)+"\n";
            cursor.moveToNext();
        }
        db.close();
        return result;
    }
    public void updateRecord(String id,String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,name);
        db.update(TABLE_NAME,values,"id=?",new String[]{id});
        db.close();
    }
    public void deleteRecord(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{id});

        db.close();
    }
}