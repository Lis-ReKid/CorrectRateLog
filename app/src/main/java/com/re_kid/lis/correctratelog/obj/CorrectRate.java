package com.re_kid.lis.correctratelog.obj;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CorrectRate {
    private final int correctNum;
    private final int entireNum;
    private final double correctRate;
    public CorrectRate(final String correctNum, final String entireNum) {
        final int intCorrect = Integer.parseInt(correctNum);
        final int intEntire = Integer.parseInt(entireNum);
        // 正答数0未満でエラー
        if (intCorrect < 0) throw new IllegalArgumentException("正答数は0以上の数値を指定してください。");
        // 問題数1未満でエラー
        if (intEntire < 1) throw new IllegalArgumentException("問題数は1以上の数値を指定してください。");
        // 正答数>問題数でエラー
        if (intCorrect > intEntire) throw new IllegalArgumentException("正答数が問題数を超えています。");

        this.correctNum = intCorrect;
        this.entireNum = intEntire;

        // 正答率を初期化
        // 小数点以下3桁のdouble型
        BigDecimal decDividend = new BigDecimal(intCorrect);
        BigDecimal decDivisor = new BigDecimal(intEntire);
        this.correctRate =  decDividend.divide(decDivisor, 3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * double型で正答率を返します。
     * @return 率（小数点以下３桁）
     */
    public double getCorrectRate() {
        return correctRate;
    }
}
