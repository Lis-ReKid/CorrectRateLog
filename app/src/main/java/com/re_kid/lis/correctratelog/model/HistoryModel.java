package com.re_kid.lis.correctratelog.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.History;

import java.util.List;

public class HistoryModel{
    SQLiteDatabase _db;

    public HistoryModel(SQLiteDatabase db) {
        _db = db;
    }
    public void createHistory(final History history) {
        // SQLを作成
        var sqlInsert = "INSERT INTO Histories " +
                "(category_id, learned_date, learned_time, correct_number, entire_number, correct_rate)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = _db.compileStatement(sqlInsert);
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
        var sql = "SELECT * FROM v_Histories ORDER BY _id DESC";
        return _db.rawQuery(sql, null);
    }

    public Cursor selectByCategory(Category category) {
        var sql = "SELECT * FROM v_Histories WHERE category_id = " +
                category.getId() + " " +
                "ORDER BY _id DESC";
        return _db.rawQuery(sql, null);
    }
    public void update(History history) {
        // sql作成
        var sql = "UPDATE Histories " +
                "SET category_id = ?," +
                "learned_date = ?, " +
                "learned_time = ?, " +
                "correct_number = ?, " +
                "entire_number = ?, " +
                "correct_rate = ? " +
                " WHERE _id = ?";
        SQLiteStatement stmt = _db.compileStatement(sql);
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
        var sqlDelete = "DELETE FROM Histories WHERE _id = ?";
        SQLiteStatement stmt = _db.compileStatement(sqlDelete);
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();
    }

    public void deleteAll() {
        var sqlDelete = "DELETE FROM Histories";
        SQLiteStatement stmt = _db.compileStatement(sqlDelete);
        stmt.executeUpdateDelete();
    }

    public void migrate(List<History> historyList) {
        for (History history : historyList) {
            // SQLを作成
            var sqlInsert = "INSERT INTO Histories " +
                    "(_id, category_id, learned_date, learned_time, correct_number, entire_number, correct_rate)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement stmt = _db.compileStatement(sqlInsert);
            stmt.bindLong(1, history.getId());
            stmt.bindLong(2, history.getCategory().getId());
            stmt.bindString(3, history.getLearnedDate().toString());
            stmt.bindString(4, history.getLearnedTime().toString());
            stmt.bindLong(5, history.getCorrectNum());
            stmt.bindLong(6, history.getEntireNum());
            stmt.bindDouble(7, history.getCorrectRate().getCorrectRate());
            // SQLを実行
            stmt.executeInsert();
        }
    }
}
