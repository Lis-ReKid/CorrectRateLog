package com.re_kid.lis.correctratelog.model;

import android.content.Context;

import com.re_kid.lis.correctratelog.DatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoriesModel implements AutoCloseable {
    DatabaseHelper _helper;

    public HistoriesModel(Context context) {
        _helper = new DatabaseHelper(context);
    }
    public void createHistory(final String learnedDateTime,
                              final int correctNum,
                              final int entireNum,
                              final double correctRate) {
    }
    public Map<String, String> getHistories (List<String> columns) {
        return new HashMap<>();
    }

    @Override
    public void close() throws Exception {
        _helper.close();
    }
}
