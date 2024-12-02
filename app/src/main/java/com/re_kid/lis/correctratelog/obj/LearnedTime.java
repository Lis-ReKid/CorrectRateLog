package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LearnedTime {
    final private String learnedTime;

    public LearnedTime(String learnedTime) {
        this.learnedTime = learnedTime;
    }

    /**
     * 指定の時間で初期化したLearnedTimeインスタンスを取得
     * @param hourOfDay 時
     * @param minute 分
     * @return LearnedTimeインスタンス
     */
    public static LearnedTime of(int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.getDefault();
        sb.append(String.format(locale, "%02d", hourOfDay));
        sb.append(":");
        sb.append(String.format(locale, "%02d", minute));
        return new LearnedTime(sb.toString());
    }
    @NonNull
    @Override
    public String toString() {
        return this.learnedTime;
    }
}
