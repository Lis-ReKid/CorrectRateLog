package com.re_kid.lis.correctratelog.obj;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class History implements Parcelable {
    private final int id;
    private final Category category;
    private final LearnedDate learnedDate;
    private final LearnedTime learnedTime;
    private final int correctNum;
    private final int entireNum;
    private final CorrectRate correctRate;
    private final static int MAX = 9999;

    public History(int id, Category category, LearnedDate learnedDate, LearnedTime learnedTime,
                   int correctNum, int entireNum, CorrectRate correctRate) {
        this.id = id;
        // 正答数・問題数が最大値を超えるとエラー
        if (correctNum > MAX) throw new IllegalArgumentException("正答数は9999以下の数値を指定してください。");
        if (entireNum > MAX) throw new IllegalArgumentException("問題数は9999以下の数値を指定してください。");

        this.category = category;
        this.learnedDate = learnedDate;
        this.learnedTime = learnedTime;
        this.correctNum = correctNum;
        this.entireNum = entireNum;
        this.correctRate = correctRate;
    }

    protected History(Parcel in) {
        id = in.readInt();
        category = in.readParcelable(Category.class.getClassLoader());
        learnedDate = in.readParcelable(LearnedDate.class.getClassLoader());
        learnedTime = in.readParcelable(LearnedTime.class.getClassLoader());
        correctNum = in.readInt();
        entireNum = in.readInt();
        correctRate = in.readParcelable(CorrectRate.class.getClassLoader());
    }

    public static final Creator<History> CREATOR = new Creator<>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(category, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeParcelable(learnedDate, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeParcelable(learnedTime, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeInt(correctNum);
        dest.writeInt(entireNum);
        dest.writeParcelable(correctRate, PARCELABLE_WRITE_RETURN_VALUE);
    }

    public int getId() {
        return id;
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
     * @param cursor Historiesテーブルから取得したCursor
     * @return Historyオブジェクト
     */
    public static History parse(Cursor cursor) {
        // 列の内容を取得
        var idIndex = cursor.getColumnIndex("_id");
        var id = cursor.getInt(idIndex);
        var categoryIdIndex = cursor.getColumnIndex("category_id");
        var categoryNameIndex = cursor.getColumnIndex("category_name");
        var category = new Category(cursor.getInt(categoryIdIndex),
                cursor.getString(categoryNameIndex));
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

        return new History(id, category, LearnedDate.parse(date), LearnedTime.parse(time),
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
}
