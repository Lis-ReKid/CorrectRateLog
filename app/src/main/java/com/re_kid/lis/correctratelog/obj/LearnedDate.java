package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LearnedDate {
    private final String learnedDate;

    public LearnedDate(String learnedDate) {
        this.learnedDate = learnedDate;
    }

    /**
     * 指定の年月日で初期化したLearnedDateインスタンスを取得
     * @param year 西暦
     * @param month 月
     * @param dayOfMonth 日
     * @return 初期化したLearnedDateインスタンスを取得
     */
    public static LearnedDate of(int year, int month, int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.getDefault();
        sb.append(String.format(locale, "%04d", year));
        sb.append("/");
        sb.append(String.format(locale, "%02d", month));
        sb.append("/");
        sb.append(String.format(locale, "%02d", dayOfMonth));
        return new LearnedDate(sb.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return this.learnedDate;
    }
}
