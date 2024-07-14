package com.re_kid.lis.accuracychecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "accuracychecker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_HISTORIES = "CREATE TABLE Histories ("
            + "_id INTEGER PRIMARY KEY NOT NULL, "
            + "history_date TEXT NOT NULL, "
            + "accurate_number INTEGER NOT NULL, "
            + "entire_number INTEGER NOT NULL)";
    private static final String CREATE_TAGS = "CREATE TABLE Tags("
            + "_id INTEGER PRIMARY KEY NOT NULL, "
            + "tag_name TEXT NOT NULL)";
    private static final String CREATE_HIST_TAG = "CREATE TABLE Hist_Tag("
            + "_id INTEGER PRIMARY KEY NOT NULL, "
            + "history_id INTEGER NOT NULL, "
            + "tag_id INTEGER NOT NULL,  "
            + "FOREIGN KEY(history_id) REFERENCES Histories(_id) ON DELETE CASCADE, "
            + "FOREIGN KEY(tag_id) REFERENCES Tags(_id) ON DELETE CASCADE)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HISTORIES);
        db.execSQL(CREATE_TAGS);
        db.execSQL(CREATE_HIST_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
