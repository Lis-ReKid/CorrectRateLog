package com.re_kid.lis.correctratelog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "correctratelog.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_HISTORIES = "CREATE TABLE Histories ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "history_datetime TEXT NOT NULL, "
            + "correct_number INTEGER NOT NULL, "
            + "entire_number INTEGER NOT NULL, "
            + "correct_rate REAL NOT NULL)";
    private static final String CREATE_TAGS = "CREATE TABLE Tags("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "tag_name TEXT NOT NULL, "
            + "tag_order INTEGER NOT NULL)";
    private static final String CREATE_HIST_TAG = "CREATE TABLE Hist_Tag("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "history_id INTEGER NOT NULL, "
            + "tag_id INTEGER NOT NULL, "
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
