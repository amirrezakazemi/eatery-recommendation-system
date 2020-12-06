package com.example.androidfinalproject.controller;

import android.app.Application;

import com.example.androidfinalproject.model.database.CommentDatabase;
import com.example.androidfinalproject.model.database.DatabaseOperation;

public class AppConf extends Application {

    private static DatabaseOperation databaseOperation;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseOperation = new DatabaseOperation(this);
    }

    public static DatabaseOperation getDatabaseOperation() {
        return databaseOperation;
    }

}