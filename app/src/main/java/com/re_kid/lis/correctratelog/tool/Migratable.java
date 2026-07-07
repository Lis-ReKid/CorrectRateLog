package com.re_kid.lis.correctratelog.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_kid.lis.correctratelog.DatabaseHelper;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.MigrationData;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public interface Migratable {
    default String issueId(Context context) throws JsonProcessingException {
        // Historyリストを取得
        var historyModel = new HistoryModel(DatabaseHelper.getSQLiteDatabase(context));
        var histories = History.getHistories(historyModel.selectAll());
        // Categoryリストを取得
        var categoryModel = new CategoryModel(DatabaseHelper.getSQLiteDatabase(context));
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
            URL url = new URL(context.getString(R.string.api_base_url) + context.getString(R.string.api_issue_endpoint));
            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(3000);
//            con.setReadTimeout(3000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            os = con.getOutputStream();
            os.write(migrateDataJson.getBytes(StandardCharsets.UTF_8));
            os.flush();
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
            // InputStreamを解放
            IOUtils.closeQuietly(is);
        }
    }

    default boolean migrate(Context context, String id) {
        boolean result = false;
        // 移行データを取得
        HttpURLConnection con = null;
        InputStream is = null;
        MigrationData data = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL(context.getString(R.string.api_base_url) + context.getString(R.string.api_migrate_endpoint) + "/" + id);
            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(3000);
//            con.setReadTimeout(3000);
            con.setRequestMethod("GET");
            con.connect();
            // 該当データがない時引継ぎ失敗
            if (con.getResponseCode() == 404) return false;
            is = con.getInputStream();
            data = mapper.readValue(is, MigrationData.class);
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", "URL変換エラー");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Category> categoryList = Category.stringList2CategoryList(data.getCategories());
        List<History> historyList = History.stringList2CategoryList(data.getHistories());
        SQLiteDatabase db = DatabaseHelper.getSQLiteDatabase(context);
        try {
            // トランザクション開始
            db.beginTransaction();
            // Model取得
            CategoryModel categoryModel = new CategoryModel(db);
            HistoryModel historyModel = new HistoryModel(db);
            // 既存のデータ全部消す
            categoryModel.deleteAll();
            historyModel.deleteAll();
            // 新規データ入れる
            categoryModel.migrate(categoryList);
            historyModel.migrate(historyList);
            // トランザクション成功をマーク
            db.setTransactionSuccessful();
            result = true;
        } finally {
            db.endTransaction();
            db.close();
        }
        // トランザクション終了
        return result;
    }
}
