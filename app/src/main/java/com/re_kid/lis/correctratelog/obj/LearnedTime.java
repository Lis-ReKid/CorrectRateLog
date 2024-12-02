package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.util.Locale;
import java.util.regex.Pattern;

public class LearnedTime {
    final private String learnedTime;

    public LearnedTime(final String learnedTime) {
        // 桁数チェック
        String regex = "\\d{2}:\\d{2}";
        if (!Pattern.matches(regex, learnedTime)) {throw new IllegalArgumentException("時間が不正な値です。");}

        this.learnedTime = learnedTime;
    }

    /**
     * 指定の時間で初期化したLearnedTimeインスタンスを取得
     * @param hourOfDay 時
     * @param minute 分
     * @return LearnedTimeインスタンス
     */
    public static LearnedTime of(final int hourOfDay, final int minute) {
        StringBuilder sb = new StringBuilder();
        final Locale locale = Locale.getDefault();
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
