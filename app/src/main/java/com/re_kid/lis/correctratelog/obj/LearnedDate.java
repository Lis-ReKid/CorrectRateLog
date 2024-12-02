package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.util.Locale;
import java.util.regex.Pattern;

public class LearnedDate {
    private final String learnedDate;

    public LearnedDate(final String learnedDate) {
        // 桁数チェック
        final String regex = "\\d{4}/\\d{2}/\\d{2}";
        if (!Pattern.matches(regex, learnedDate)) {throw new IllegalArgumentException("日付が不正な値です。");}

        this.learnedDate = learnedDate;
    }

    /**
     * 指定の年月日で初期化したLearnedDateインスタンスを取得
     * @param year 西暦
     * @param month 月
     * @param dayOfMonth 日
     * @return LearnedDateインスタンス
     */
    public static LearnedDate of(final int year, final int month, final int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        final Locale locale = Locale.getDefault();
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
