package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LearnedDate {
    private final LocalDate learnedDate;
    private final Locale _LOCALE =  Locale.getDefault();
    private final DateTimeFormatter _FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd", _LOCALE);

    private LearnedDate(final String learnedDate) {
        this.learnedDate = LocalDate.parse(learnedDate, _FORMATTER);
    }
    private LearnedDate(final int year, final int month, final int dayOfMonth) {
        this.learnedDate = LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * 指定の年月日で初期化したLearnedDateインスタンスを取得
     * @param learnedDate 年月日(yyyy/MM/dd)
     * @return LearnedDateインスタンス
     */
    public static LearnedDate parse(final String learnedDate) {
        return new LearnedDate(learnedDate);
    }

    /**
     * 指定の年月日で初期化したLearnedDateインスタンスを取得
     * @param year 西暦
     * @param month 月
     * @param dayOfMonth 日
     * @return LearnedDateインスタンス
     */
    public static LearnedDate of(final int year, final int month, final int dayOfMonth) {
        return new LearnedDate(year, month, dayOfMonth);
    }

    @NonNull
    @Override
    public String toString() {
        return learnedDate.format(_FORMATTER);
    }
}
