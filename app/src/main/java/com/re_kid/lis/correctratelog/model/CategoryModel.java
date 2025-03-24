package com.re_kid.lis.correctratelog.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.obj.Category;

import java.util.List;

public class CategoryModel {
    SQLiteDatabase _db;

    public CategoryModel(SQLiteDatabase db) {
        this._db = db;
    }

    public void create(final Category category) {
        // SQLを作成
        var sqlInsert = "INSERT INTO Categories" +
                "(category_name)" +
                "VALUES (?)";
        SQLiteStatement stmt = _db.compileStatement(sqlInsert);
        stmt.bindString(1, category.getCategoryName());
        // SQLを実行
        stmt.executeInsert();
    }

    public Cursor selectAll() {
        // SQLを作成
        var sql = "SELECT * FROM Categories ORDER BY _id DESC";
        return _db.rawQuery(sql, null);
    }

    public void delete(Category category) {
        var sql = "DELETE FROM Categories WHERE _id = ?";
        SQLiteStatement stmt = _db.compileStatement(sql);
        var id = category.getId();
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();
    }

    public void deleteAll() {
        var sqlDelete = "DELETE FROM Categories";
        SQLiteStatement stmt = _db.compileStatement(sqlDelete);
        stmt.executeUpdateDelete();
    }

    public void migrate(List<Category> categoryList) {
        for (Category category : categoryList) {
            // SQLを作成
            var sqlInsert = "INSERT INTO Categories" +
                    "(_id, category_name)" +
                    "VALUES (?, ?)";
            SQLiteStatement stmt = _db.compileStatement(sqlInsert);
            stmt.bindString(1, String.valueOf(category.getId()));
            stmt.bindString(2, category.getCategoryName());
            // SQLを実行
            stmt.executeInsert();
        }
    }
}
