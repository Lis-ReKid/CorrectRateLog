package com.re_kid.lis.correctratelog.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.re_kid.lis.correctratelog.DatabaseHelper;
import com.re_kid.lis.correctratelog.obj.Category;

public class CategoryModel implements AutoCloseable{
    DatabaseHelper _helper;

    public CategoryModel(Context context) {
        this._helper = new DatabaseHelper(context);
    }

    public void create(final Category category) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQLを作成
        var sqlInsert = "INSERT INTO Categories" +
                "(category_name)" +
                "VALUES (?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, category.getCategoryName());
        // SQLを実行
        stmt.executeInsert();
    }

    public Cursor selectAll() {
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQLを作成
        var sql = "SELECT * FROM Categories ORDER BY _id DESC";
        return db.rawQuery(sql, null);
    }

    public void delete(Category category) {
        SQLiteDatabase db = _helper.getWritableDatabase();
        var sql = "DELETE FROM Categories WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        var id = category.getId();
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();
    }
    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
