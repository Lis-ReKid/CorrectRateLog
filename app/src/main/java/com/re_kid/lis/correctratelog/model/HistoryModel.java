package com.re_kid.lis.correctratelog.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.DatabaseHelper;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.History;

public class HistoryModel implements AutoCloseable {
    DatabaseHelper _helper;

    public HistoryModel(Context context) {
        _helper = new DatabaseHelper(context);
    }
    public void createHistory(final History history) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQLを作成
        var sqlInsert = "INSERT INTO Histories " +
                "(category_id, learned_date, learned_time, correct_number, entire_number, correct_rate)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindLong(1, history.getCategory().getId());
        stmt.bindString(2, history.getLearnedDate().toString());
        stmt.bindString(3, history.getLearnedTime().toString());
        stmt.bindLong(4, history.getCorrectNum());
        stmt.bindLong(5, history.getEntireNum());
        stmt.bindDouble(6, history.getCorrectRate().getCorrectRate());
        // SQLを実行
        stmt.executeInsert();
    }
    public Cursor selectAll() {
        SQLiteDatabase db = _helper.getWritableDatabase();
        var sql = "SELECT * FROM v_Histories ORDER BY _id DESC";
        return db.rawQuery(sql, null);
    }

    public Cursor selectByCategory(Category category) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        var sql = "SELECT * FROM v_Histories WHERE category_id = " +
                category.getId() +
                "ORDER BY _id DESC";
        return db.rawQuery(sql, null);
    }
    public void update(History history) {
        // sql作成
        SQLiteDatabase db = _helper.getWritableDatabase();
        var sql = "UPDATE Histories " +
                "SET category_id = ?," +
                "learned_date = ?, " +
                "learned_time = ?, " +
                "correct_number = ?, " +
                "entire_number = ?, " +
                "correct_rate = ? " +
                " WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        // 各変数をバインド
        stmt.bindLong(1, history.getCategory().getId());
        stmt.bindString(2, history.getLearnedDate().toString());
        stmt.bindString(3, history.getLearnedTime().toString());
        stmt.bindLong(4, history.getCorrectNum());
        stmt.bindLong(5, history.getEntireNum());
        stmt.bindDouble(6, history.getCorrectRate().getCorrectRate());
        stmt.bindLong(7, history.getId());
        // 更新
        stmt.executeUpdateDelete();
    }
    public void delete(int id) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        var sqlDelete = "DELETE FROM Histories WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sqlDelete);
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();
    }

    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
