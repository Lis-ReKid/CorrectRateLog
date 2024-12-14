package com.re_kid.lis.correctratelog.obj;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class History {
    private final LearnedDate learnedDate;
    private final LearnedTime learnedTime;
    private final int correctNum;
    private final int entireNum;
    private final CorrectRate correctRate;

    public History(LearnedDate learnedDate, LearnedTime learnedTime,
                   int correctNum, int entireNum, CorrectRate correctRate) {
        this.learnedDate = learnedDate;
        this.learnedTime = learnedTime;
        this.correctNum = correctNum;
        this.entireNum = entireNum;
        this.correctRate = correctRate;
    }

    public LearnedDate getLearnedDate() {
        return learnedDate;
    }

    public LearnedTime getLearnedTime() {
        return learnedTime;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public int getEntireNum() {
        return entireNum;
    }

    public CorrectRate getCorrectRate() {
        return correctRate;
    }

    /**
     * Historiesテーブルから取得したCursorをHistoryに変換
     * @param cursor
     * @return
     */
    private static History parse(Cursor cursor) {
        // 列の内容を取得
        var dateIndex = cursor.getColumnIndex("learned_date");
        var date = cursor.getString(dateIndex);
        var timeIndex = cursor.getColumnIndex("learned_time");
        var time = cursor.getString(timeIndex);
        var correctNumIndex = cursor.getColumnIndex("correct_number");
        var correctNum = cursor.getInt(correctNumIndex);
        var entireNumIndex = cursor.getColumnIndex("entire_number");
        var entireNum = cursor.getInt(entireNumIndex);
        var correctRateIndex = cursor.getColumnIndex("correct_rate");
        var correctRate = cursor.getDouble(correctRateIndex);

        return new History(LearnedDate.parse(date), LearnedTime.parse(time),
                correctNum, entireNum, new CorrectRate(correctRate));
    }

    /**
     * Historiesテーブルから取得したCursorオブジェクトをListに変換
     * @param cursor HistoriesのCursorオブジェクト
     * @return HistoryのList
     */
    public static List<History> getHistories(Cursor cursor) {
        List<History> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            // Historyオブジェクトを生成してListに格納
            list.add(History.parse(cursor));
        }
        return list;
    }

    public static Bundle getBundle(Cursor cursor) {
        var bundle = new Bundle();
        var history = History.parse(cursor);
        var idIndex = cursor.getColumnIndex("_id");
        bundle.putInt("id", cursor.getInt(idIndex));
        bundle.putString("date", history.getLearnedDate().toString());
        bundle.putString("date", history.getLearnedDate().toString());
        bundle.putInt("correctNum", history.getCorrectNum());
        bundle.putInt("entireNum", history.getEntireNum());
        bundle.putDouble("CorrectRate", history.getCorrectRate().getCorrectRate());
        return bundle;
    }
}
