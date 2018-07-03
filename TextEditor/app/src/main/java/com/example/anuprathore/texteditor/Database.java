package com.example.anuprathore.texteditor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//User defined class name 'Database' inheriting buildin class SQLiteOpenHelper
public  class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME= "aksr.db";
    private static final String TABLE_NAME="notes";
    private static final int DATABASE_VERSION=1;
    //Database version needs to be incremented as per modification in schema of database 

    Database(Context context) {//constructor of class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    //This method executes only once on creation of database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE VARCHAR(50),NOTE VARCHAR(500), TIME DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    //This method executes on upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Function to insert heading and note in database
    long insertdata(String title, String data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("TITLE",title);
        content.put("NOTE",data);
        long result =db.insert(TABLE_NAME,null,content);
        db.close();
        return result;
    }

    //Cursor contains the result of the query
    //This method is used to fetch the title,timestamp and auto incremented 
    //integer value from the database. 
	Cursor getHeading(){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={"ID","TITLE","TIME"};
        return db.query(TABLE_NAME,columns,null,null,null,null,"ID DESC");
	}

	//This method fetches all the values from the database.
    Cursor getData(String id,String title){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={"ID","TITLE","NOTE","TIME"};
        String where="ID = ? AND TITLE = ?";
        String[] whereArgs=new String[]{id,title};
        return db.query(TABLE_NAME,columns,where,whereArgs,null,null,null);
    }

    //This method is used to update the database by using id and title.
    //This method returns an integer value if it will be unsuccessful it will return -1
    long updatedata(String id,String title,String newTitle,String newNote){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("TITLE",newTitle);
        content.put("NOTE",newNote);
        String where="ID = ? AND TITLE = ?";//Each value of '?' will be replaced by 
        String[] whereArgs=new String[]{id,title};//respective string items from whereArgs
        return db.update(TABLE_NAME,content,where,whereArgs);
    }

    //This method helps in implementing delete operation of notes
    //This method returns an integer value if it will be unsuccessful it will return -1
    long deletedata(String id,String title){
        SQLiteDatabase db=this.getWritableDatabase();
        String where="ID = ? AND TITLE = ?";
        String[] whereArgs=new String[]{id,title};
        long res=db.delete(TABLE_NAME,where,whereArgs);
        db.close();
        return res;
    }
}