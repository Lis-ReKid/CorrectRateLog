package com.re_kid.lis.correctratelog.obj;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class MigrationData {
    private final List<String> histories;
    private final List<String> categories;
    public MigrationData(){
        this.histories = null;
        this.categories = null;
    }

    public MigrationData(List<History> histories, List<Category> categories) {
        var mapper = new ObjectMapper();
        List<String> historyList = new ArrayList<>();
        for (History history : histories) {
            try {
                historyList.add(mapper.writeValueAsString(history));
            } catch (JsonProcessingException e) {
                Log.e("JSONError", "HistoryクラスのJSON変換に失敗しました。");
                throw new RuntimeException(e);
            }
        }
        List<String> categoryList = new ArrayList<>();
        for (Category category : categories) {
            try {
                categoryList.add(mapper.writeValueAsString(category));
            } catch (JsonProcessingException e) {
                Log.e("JSONError", "CategoryクラスのJSON変換に失敗しました。");
                throw new RuntimeException(e);
            }
        }
        this.histories = historyList;
        this.categories = categoryList;
    }

    public List<String> getHistories() {
        return histories;
    }

    public List<String> getCategories() {
        return categories;
    }
}
