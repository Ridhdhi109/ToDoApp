package com.example.ridhdhi.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ridhdhi on 6/27/16.
 */
public class ToDoDatabaseHelper extends SQLiteOpenHelper {

    private static ToDoDatabaseHelper sInstance;

    public static final String DATABASE_NAME = "toDoDatabase";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_TODOITEMS = "to_do_items";

    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_ITEM_NAME = "item_name";

    private ToDoDatabaseHelper (Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOITEM_TABLE = "CREATE TABLE " + TABLE_TODOITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY"+")";
        db.execSQL(CREATE_TODOITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEMS);
            onCreate(db);
        }
    }

    public static  synchronized ToDoDatabaseHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ToDoDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

}
