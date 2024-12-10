package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class CorrectRate {
    private final double correctRate;
    public CorrectRate(final int correctNum, final int entireNum) {
        // 正答数0未満でエラー
        if (correctNum < 0) throw new IllegalArgumentException("正答数は0以上の数値を指定してください。");
        // 問題数1未満でエラー
        if (entireNum < 1) throw new IllegalArgumentException("問題数は1以上の数値を指定してください。");
        // 正答数>問題数でエラー
        if (correctNum > entireNum) throw new IllegalArgumentException("正答数が問題数を超えています。");

        // 正答率を初期化
        // 小数点以下3桁のdouble型
        BigDecimal bdCorrectNum = new BigDecimal(correctNum);
        BigDecimal bdEntireNum = new BigDecimal(entireNum);
        this.correctRate =  bdCorrectNum.divide(bdEntireNum, 3, RoundingMode.HALF_UP).doubleValue();
    }

    public CorrectRate(final double correctRate) {
        this.correctRate = correctRate;
    }

    /**
     * double型で正答率を返します。
     * @return 率（小数点以下３桁）
     */
    public double getCorrectRate() {
        return correctRate;
    }

    public static CorrectRate getTotalCorrectRate(List<History> histories) {
        int totalCorrectNum = 0;
        int totalEntireNum = 0;
        for (History history : histories) {
            totalCorrectNum += history.getCorrectNum();
            totalEntireNum += history.getEntireNum();
        }
        return new CorrectRate(totalCorrectNum, totalEntireNum);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%.1f", correctRate * 100);
    }
}
