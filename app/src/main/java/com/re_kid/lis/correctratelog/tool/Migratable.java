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

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
        String migrateDataJson = mapper.writeValueAsString(migrationData);
        System.out.println(migrateDataJson);

        HttpURLConnection con = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            URL url = new URL("http://10.0.2.2:8080/issue");
            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(3000);
//            con.setReadTimeout(3000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            os = con.getOutputStream();
            os.write(migrateDataJson.getBytes(StandardCharsets.UTF_8));
            os.flush();
            System.out.println(con.getResponseCode());
            is = con.getInputStream();
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", "URL変換エラー");
            return "connection failed";
        } catch (IOException e) {
            Log.e("IOException", "入出力エラー", e);
            return "通信に失敗しました。時間を置いてお試しください。";
        } finally {
            // HttpConnectionを解放
            if (con != null) {
                con.disconnect();
            }
            // OutputStreamを解放
            IOUtils.closeQuietly(os);
        }
    }
}
