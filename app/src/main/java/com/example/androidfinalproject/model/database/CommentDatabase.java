package com.example.androidfinalproject.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidfinalproject.model.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommentDatabase {
    SQLiteDatabase db;

    public CommentDatabase(Context context) {
        CommentDBOpenHelper helper = new CommentDBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(String placeId, String comment) {
        ContentValues v = new ContentValues();
        v.put(DatabaseConstants.CommentsTable.PLACE_ID, placeId);
        v.put(DatabaseConstants.CommentsTable.COMMENT, comment);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(Calendar.getInstance().getTime());
        v.put(DatabaseConstants.CommentsTable.TIME, String.valueOf(formattedDate));
        db.insert(DatabaseConstants.CommentsTable.TABLE_NAME, null, v);
    }

    public ArrayList<Comment> query(String id) {

        Cursor cursor = db.query(DatabaseConstants.CommentsTable.TABLE_NAME,
                null, DatabaseConstants.CommentsTable.PLACE_ID + "= ?",
                new String[]{id}, null, null, null);
        ArrayList<Comment> comments = new ArrayList<>();
        String placeId;
        String comment;
        String time;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            placeId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.CommentsTable.PLACE_ID));
            comment = cursor.getString(cursor.getColumnIndex(DatabaseConstants.CommentsTable.COMMENT));
            time = cursor.getString(cursor.getColumnIndex(DatabaseConstants.CommentsTable.TIME));
            cursor.moveToNext();
            comments.add(new Comment(placeId, comment, time));
        }
        return comments;
    }


}