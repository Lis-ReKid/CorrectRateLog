package com.re_kid.lis.correctratelog.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.DatabaseHelper;
import com.re_kid.lis.correctratelog.obj.History;

public class HistoryModel implements AutoCloseable {
    DatabaseHelper _helper;

    public HistoryModel(Context context) {
        _helper = new DatabaseHelper(context);
    }
    public void createHistory(final History history) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQLを作成
        String sqlInsert = "INSERT INTO Histories " +
                "(learned_date, learned_time, correct_number, entire_number, correct_rate)" +
                "VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, history.getLearnedDate().toString());
        stmt.bindString(2, history.getLearnedTime().toString());
        stmt.bindLong(3, history.getCorrectNum());
        stmt.bindLong(4, history.getEntireNum());
        stmt.bindDouble(5, history.getCorrectRate().getCorrectRate());
        // SQLを実行
        stmt.executeInsert();
    }
    public Cursor selectAll() {
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "SELECT * FROM Histories ORDER BY _id DESC";
        return db.rawQuery(sql, null);
    }

    public void delete(int id) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlDelete = "DELETE FROM Histories WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sqlDelete);
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();
    }

    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
