package com.re_kid.lis.correctratelog.obj;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parcelable {
    private final int id;
    private final String categoryName;

    public Category(int id, String name) {
        this.id = id;
        this.categoryName = name;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        categoryName = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryName);
    }

    public static Category parse(Cursor cursor) {
        var idIndex = cursor.getColumnIndex("_id");
        var id = cursor.getInt(idIndex);
        var categoryNameIndex = cursor.getColumnIndex("category_name");
        var categoryName = cursor.getString(categoryNameIndex);
        return new Category(id, categoryName);
    }

    public static List<Category> getCategories(Cursor cursor) {
        List<Category> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            // Historyオブジェクトを生成してListに格納
            list.add(Category.parse(cursor));
        }
        return list;
    }

    public static List<Category> stringList2CategoryList(List<String> categoryStrings) {
        List<Category> categoryList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String categorystring : categoryStrings) {
            try {
                categoryList.add(mapper.readValue(categorystring, Category.class));
            } catch (JsonProcessingException e) {
                Log.e("JSONError", "CategoryクラスのJSON変換に失敗しました。");
                throw new RuntimeException(e);
            }
        }
        return categoryList;
    }
}
