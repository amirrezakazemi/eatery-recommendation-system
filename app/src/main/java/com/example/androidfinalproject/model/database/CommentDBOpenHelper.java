package com.example.androidfinalproject.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CommentDBOpenHelper extends SQLiteOpenHelper {
    public CommentDBOpenHelper(Context context) {
        super(context, DatabaseConstants.C_DATABASE_NAME, null, DatabaseConstants.C_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseConstants.CommentsTable.CREATE_COMMENT_TABLE_SYNTAX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

