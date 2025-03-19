package com.re_kid.lis.correctratelog.tool;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.Greeting;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.MigrationData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public interface Migratable {
    default String getGreeting(Context context) throws JsonProcessingException {
        // Historyリストを取得
        var historyModel = new HistoryModel(context);
        var histories = History.getHistories(historyModel.selectAll());
        // Categoryリストを取得
        var categoryModel = new CategoryModel(context);
        var categories = Category.getCategories(categoryModel.selectAll());
        // データ移行クラスを取得
        var migrationData = new MigrationData(histories, categories);
        // dataをJSON化
        var mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(migrationData);
        System.out.println(json);
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL("http://10.0.2.2:8080/greet");
            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(3000);
//            con.setReadTimeout(3000);
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            Greeting greeting = mapper.readValue(is, Greeting.class);
            return greeting.getGreeting();
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", "URL変換エラー");
            return "connection failed";
        } catch (IOException e) {
            Log.e("IOException", "入出力エラー", e);
            return "connection failed";
        }
    }
}
