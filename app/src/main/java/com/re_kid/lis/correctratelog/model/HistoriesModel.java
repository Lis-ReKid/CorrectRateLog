package com.re_kid.lis.correctratelog.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.DatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoriesModel implements AutoCloseable {
    DatabaseHelper _helper;

    public HistoriesModel(Context context) {
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
    public Map<String, String> getHistories (List<String> columns) {
        return new HashMap<>();
    }

    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
