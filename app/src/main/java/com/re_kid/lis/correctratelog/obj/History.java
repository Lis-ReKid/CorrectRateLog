package com.re_kid.lis.correctratelog.obj;

import android.database.Cursor;

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
     * Historiesテーブルから取得したCursorオブジェクトをListに変換
     * @param cursor HistoriesのCursorオブジェクト
     * @return HistoryのList
     */
    public static List<History> getHistories(Cursor cursor) {
        List<History> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            // 列の内容を取得
            int dateIndex = cursor.getColumnIndex("learned_date");
            String date = cursor.getString(dateIndex);
            int timeIndex = cursor.getColumnIndex("learned_time");
            String time = cursor.getString(timeIndex);
            int correctNumIndex = cursor.getColumnIndex("correct_number");
            int correctNum = cursor.getInt(correctNumIndex);
            int entireNumIndex = cursor.getColumnIndex("entire_number");
            int entireNum = cursor.getInt(entireNumIndex);
            int correctRateIndex = cursor.getColumnIndex("correct_rate");
            double correctRate = cursor.getDouble(correctRateIndex);

            // Historyオブジェクトを生成してListに格納
            History history = new History(LearnedDate.parse(date),
                    LearnedTime.parse(time),
                    correctNum,
                    entireNum,
                    new CorrectRate(correctRate));
            list.add(history);
        }
        return list;
    }
}
