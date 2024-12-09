package com.re_kid.lis.correctratelog.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.DatabaseHelper;

public class HistoryModel implements AutoCloseable {
    DatabaseHelper _helper;

    public HistoryModel(Context context) {
        _helper = new DatabaseHelper(context);
    }
    public void createHistory(final String learnedDate,
                              final String learnedTime,
                              final int correctNum,
                              final int entireNum,
                              final double correctRate) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQLを作成
        String sqlInsert = "INSERT INTO Histories " +
                "(learned_date, learned_time, correct_number, entire_number, correct_rate)" +
                "VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, learnedDate);
        stmt.bindString(2, learnedTime);
        stmt.bindLong(3, correctNum);
        stmt.bindLong(4, entireNum);
        stmt.bindDouble(5, correctRate);
        // SQLを実行
        stmt.executeInsert();
    }
    public Cursor getHistories () {
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "SELECT * FROM Histories ORDER BY _id DESC";
        return db.rawQuery(sql, null);
    }

    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
